package com.janita.idplugin.woodpecker.dialog;

import com.google.common.collect.Lists;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.ValidationInfo;
import com.intellij.openapi.util.Disposer;
import com.intellij.openapi.util.Pair;
import com.intellij.ui.JBSplitter;
import com.intellij.ui.components.JBCheckBox;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBTabbedPane;
import com.intellij.ui.components.JBTextField;
import com.janita.idplugin.common.constant.DataToInit;
import com.janita.idplugin.common.constant.PluginConstant;
import com.janita.idplugin.common.entity.CrDeveloper;
import com.janita.idplugin.common.entity.CrQuestion;
import com.janita.idplugin.common.enums.CrDataStorageEnum;
import com.janita.idplugin.common.enums.CrQuestionState;
import com.janita.idplugin.common.request.CrDeveloperSaveRequest;
import com.janita.idplugin.idea.base.icons.PluginIcons;
import com.janita.idplugin.idea.base.util.CodeEditorUtil;
import com.janita.idplugin.service.crdeveloper.ICrDeveloperService;
import com.janita.idplugin.service.crdeveloper.factory.CrDeveloperServiceFactory;
import com.janita.idplugin.service.crquestion.ICrQuestionService;
import com.janita.idplugin.service.crquestion.domain.CrQuestionSaveRequest;
import com.janita.idplugin.service.crquestion.factory.CrQuestionFactory;
import com.janita.idplugin.woodpecker.export.CrQuestionUtils;
import com.janita.idplugin.woodpecker.setting.CrQuestionSettingUtils;
import com.janita.idplugin.woodpecker.window.table.CrQuestionTable;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.BeanUtils;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 搜索弹窗
 *
 * @author zhucj
 */
public class CrQuestionEditDialog extends DialogWrapper {

    private static final int COMBO_BOX_WITH = 100;

    private static final int DEFAULT_WIDTH = 600;

    private static final int DEFAULT_HEIGHT = 630;

    private final JButton manualBtn = new JButton("手动指派");

    private final JPanel manualAssignPanel = new JPanel(new BorderLayout());

    private final JBTextField manualNameField = new JBTextField(10);

    private final JBTextField manualPhoneField = new JBTextField(10);

    private final CrQuestion question;

    /**
     * 如果是更新，则会保存一份拷贝，便于笔记是否真的发生变化
     */
    private final CrQuestion rawQuestion = new CrQuestion();

    private final boolean add;

    private final Integer editIndex;

    private final Editor questionCodeEditor;

    private final Editor betterCodeEditor;

    private final Editor descCodeEditor;

    private final JBCheckBox sendWeChatBox = new JBCheckBox("发送消息");

    private final ComboBox<String> typeBox = new ComboBox<>(DataToInit.QUESTION_TYPE_LIST.toArray(new String[0]), COMBO_BOX_WITH);

    private final ComboBox<String> levelBox = new ComboBox<>(DataToInit.LEVEL_LIST.toArray(new String[0]), COMBO_BOX_WITH);

    private final ComboBox<String> stateBox = new ComboBox<>(CrQuestionState.getDescArray(), COMBO_BOX_WITH);

    private final ComboBox<String> assignBox;

    /**
     * key name
     * value 开发
     */
    private final Map<String, CrDeveloper> developerMap;

    private final Project project;

    private final String rawQuestionCode;

    private boolean manualAssign = false;

    public CrQuestionEditDialog(Project project, CrQuestion question, List<CrDeveloper> developerList, boolean add, Integer editIndex) {
        super(true);
        sendWeChatBox.setSelected(add);
        this.question = question;
        BeanUtils.copyProperties(question, rawQuestion);
        this.rawQuestionCode = question.getQuestionCode();
        this.add = add;
        this.editIndex = editIndex;
        developerMap = developerList.stream().collect(Collectors.toMap(CrDeveloper::getName, Function.identity(), (f, s) -> f));
        assignBox = new ComboBox<>(developerMap.keySet().toArray(new String[0]), COMBO_BOX_WITH);
        this.project = project;
        questionCodeEditor = CodeEditorUtil.createCodeEditor(project);
        betterCodeEditor = CodeEditorUtil.createCodeEditor(project);
        descCodeEditor = CodeEditorUtil.createCodeEditor(project);
        Disposer.register(project, () -> EditorFactory.getInstance().releaseEditor(questionCodeEditor));
        Disposer.register(project, () -> EditorFactory.getInstance().releaseEditor(betterCodeEditor));
        Disposer.register(project, () -> EditorFactory.getInstance().releaseEditor(descCodeEditor));
        // 设置尺寸
        getRootPane().setMinimumSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        manualPhoneField.setToolTipText("用于发送企业微信消息，填写手机号即可");
        // 设置确定按钮标题
        setOKButtonText("保存");
        setComponentSize();
        init();

        addListener();
    }

    private void addListener() {
        manualBtn.addActionListener(e -> {
            if (manualAssign) {
                return;
            }
            setToManualAssign();
        });
        manualNameField.getDocument().addDocumentListener(new ManualFieldInput());
    }

    private void setComponentSize() {

        questionCodeEditor.getComponent().setMinimumSize(new Dimension(100, 150));
        betterCodeEditor.getComponent().setMinimumSize(new Dimension(100, 150));
        descCodeEditor.getComponent().setMinimumSize(new Dimension(100, 100));

        questionCodeEditor.getComponent().setMaximumSize(new Dimension(100, 200));
        betterCodeEditor.getComponent().setMaximumSize(new Dimension(100, 200));
        descCodeEditor.getComponent().setMaximumSize(new Dimension(100, 150));
    }

    @Override
    protected void doOKAction() {
        CrDataStorageEnum storageEnum = CrQuestionSettingUtils.getStorageWayFromCache();
        ICrQuestionService questionService = CrQuestionFactory.getCrQuestionService(storageEnum);
        ICrDeveloperService developerService = CrDeveloperServiceFactory.getICrDeveloperService(storageEnum);

        boolean sendWeChatMsg = sendWeChatBox.isSelected();
        List<String> phoneList = getPhoneList();

        // 保存开发人员
        {
            Pair<String, String> phoneAndNameOfAssignTo = getAssignerToNameAndPhone();
            CrDeveloperSaveRequest request = new CrDeveloperSaveRequest(phoneAndNameOfAssignTo.first, null, phoneAndNameOfAssignTo.second);
            developerService.save(CrQuestionSettingUtils.getCrQuestionSettingFromCache(), request);
        }

        if (add) {
            questionService.save( CrQuestionSettingUtils.getCrQuestionSettingFromCache(), new CrQuestionSaveRequest(editIndex, sendWeChatMsg, phoneList, question));
            CrQuestionTable.add(question);
        } else {
            // TODO 修改有BUG
            boolean changed = CrQuestionUtils.isChanged(rawQuestion, question);
            if (changed) {
                questionService.save(CrQuestionSettingUtils.getCrQuestionSettingFromCache(), new CrQuestionSaveRequest(editIndex, sendWeChatMsg, phoneList, question));
                CrQuestionTable.update(editIndex, question);
            }
        }
        super.doOKAction();
        dispose();
    }

    private List<String> getPhoneList() {
        Pair<String, String> assignerToNameAndPhone = getAssignerToNameAndPhone();
        if (add) {
            return Lists.newArrayList(assignerToNameAndPhone.getSecond());
        }
        return Lists.newArrayList(assignerToNameAndPhone.getSecond(), getPhoneByName(question.getAssignFrom()));
    }

    private String getPhoneByName(String name) {
        CrDeveloper developer = developerMap.get(name);
        if (developer == null) {
            return null;
        }
        return developer.getPhone();
    }

    private Pair<String, String> getAssignerToNameAndPhone() {
        if (manualAssign) {
            // 手动指派
            String name = manualNameField.getText().trim();
            String phone = manualPhoneField.getText().trim();
            return Pair.create(name, phone);
        }

        // 下拉选择
        {
            String name = (String) assignBox.getSelectedItem();
            String phone = getPhoneByName(name);
            return Pair.create(name, phone);
        }
    }

    private void rebuildQuestionWhenSave() {
        question.setType((String) typeBox.getSelectedItem());
        question.setState((String) stateBox.getSelectedItem());
        question.setLevel((String) levelBox.getSelectedItem());
        Pair<String, String> pair = getAssignerToNameAndPhone();
        question.setAssignTo(pair.getFirst());
        question.setSuggestCode(betterCodeEditor.getDocument().getText());
        question.setDescription(descCodeEditor.getDocument().getText());
    }

    @Override
    public @Nullable
    JComponent getPreferredFocusedComponent() {
        return descCodeEditor.getComponent();
    }

    @NotNull
    @Override
    protected Action[] createActions() {
        return new Action[] { getOKAction(), getCancelAction() };
    }

    @Override
    @Nullable
    protected JComponent createCenterPanel() {
        // 创建弹窗顶部的搜索参数相关的组件
        JComponent topPanel = buildCenterTopPanel();
        JPanel row1 = new JPanel(new BorderLayout());
        row1.add(topPanel, BorderLayout.NORTH);
        Box row2 = Box.createVerticalBox();
        // TAB 切换区域
        JBTabbedPane questionCodeTab = new JBTabbedPane();
        questionCodeTab.addTab("问题代码(不要修改)：", PluginIcons.Description/*左侧icon*/, questionCodeEditor.getComponent());

        JBTabbedPane betterCodeTab = new JBTabbedPane();
        betterCodeTab.addTab("建议写法：", PluginIcons.Description/*左侧icon*/, betterCodeEditor.getComponent());

        JBTabbedPane descTab = new JBTabbedPane();
        descTab.addTab("其他描述：", PluginIcons.Description/*左侧icon*/, descCodeEditor.getComponent());

        row2.add(questionCodeTab);
        row2.add(betterCodeTab);
        row2.add(descTab);
        JBSplitter p = new JBSplitter(true/*垂直的*/, 0.1F/*比例*/);
        p.setFirstComponent(row1);
        p.setSecondComponent(row2);

        return p;
    }

    @Override
    protected @Nullable
    ValidationInfo doValidate() {
        rebuildQuestionWhenSave();
        if (StringUtils.isBlank(question.getType())) {
            return new ValidationInfo("请选择问题类型");
        }
        if (StringUtils.isBlank(question.getState())) {
            return new ValidationInfo("请选择问题状态");
        }
        if (StringUtils.isBlank(question.getAssignTo())) {
            return new ValidationInfo("请把该问题指派给他人或者自己");
        }
        if (StringUtils.isBlank(question.getLevel())) {
            return new ValidationInfo("请选择问题级别");
        }
        if (StringUtils.isBlank(question.getSuggestCode()) && StringUtils.isBlank(question.getDescription())) {
            return new ValidationInfo("建议写法跟描述至少写一项");
        }
        if (!rawQuestionCode.equals(questionCodeEditor.getDocument().getText())) {
            return new ValidationInfo("问题代码不可修改");
        }
        if (question.getAssignTo().length() > 8) {
            return new ValidationInfo("指派人名称长度不能超过7个字符");
        }
        if (manualAssign) {
            String name = manualNameField.getText().trim();
            if (StringUtils.isBlank(name)) {
                return new ValidationInfo("请输入手动指派人员姓名");
            }
            String phone = manualPhoneField.getText().trim();
            if (StringUtils.isBlank(phone)) {
                return new ValidationInfo("请从企业微信拷贝该人员的手机，这样才能发送企业微信消息！");
            }
        }
        return null;
    }

    /**
     * 创建弹窗顶部的搜索参数相关的组件
     *
     * @return 创建弹窗顶部的搜索参数相关的组件
     */
    @NotNull
    private JComponent buildCenterTopPanel() {
        JPanel selectPanel = new JPanel(new BorderLayout());
        JPanel selectWestPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 0));
        selectWestPanel.add(new JBLabel("类型"));
        selectWestPanel.add(typeBox);
        selectWestPanel.add(new JBLabel("状态"));
        selectWestPanel.add(stateBox);
        selectWestPanel.add(new JBLabel("级别"));
        selectWestPanel.add(levelBox);
        selectWestPanel.add(new JBLabel("指派"));
        selectWestPanel.add(assignBox);
        selectWestPanel.add(sendWeChatBox);
        selectWestPanel.add(manualBtn);
        selectPanel.add(selectWestPanel, BorderLayout.WEST);

        JPanel manualAssignWestPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 0));
        manualAssignWestPanel.add(new JBLabel("姓名"));
        manualAssignWestPanel.add(manualNameField);
        manualAssignWestPanel.add(new JBLabel("手机"));
        manualAssignWestPanel.add(manualPhoneField);
        manualAssignPanel.add(manualAssignWestPanel, BorderLayout.WEST);
        manualAssignPanel.setVisible(false);

        Box box = Box.createVerticalBox();
        box.add(selectPanel);
        box.add(manualAssignPanel);

        return box;
    }

    public void open() {
        setQuestionDataToComponent();
        show();
    }

    private void setQuestionDataToComponent() {
        CodeEditorUtil.setCode(project, questionCodeEditor, question.getQuestionCode());
        CodeEditorUtil.setCode(project, betterCodeEditor, question.getSuggestCode());
        CodeEditorUtil.setCode(project, descCodeEditor, question.getDescription());
        CodeEditorUtil.setEditorHighlighter(questionCodeEditor, question.getLanguage());
        CodeEditorUtil.setEditorHighlighter(betterCodeEditor, question.getLanguage());
        CodeEditorUtil.setEditorHighlighter(descCodeEditor, question.getLanguage());
        if (question.getType() != null) {
            typeBox.setSelectedItem(question.getType());
        }
        if (question.getLevel() != null) {
            levelBox.setSelectedItem(question.getLevel());
        }
        if (question.getState() != null) {
            stateBox.setSelectedItem(question.getState());
        }
        if (question.getType() != null) {
            typeBox.setSelectedItem(question.getType());
        }
        if (question.getAssignTo() != null) {
            assignBox.setSelectedItem(question.getAssignTo());
        }
        setTitle(question.getProjectName() + "-" + question.getCreateGitBranchName() + "-" + question.getFileName());
        if (add) {
            stateBox.setSelectedItem(CrQuestionState.UNSOLVED.getDesc());
            stateBox.setEnabled(false);
        }
        if (emptyAssign()) {
            setToManualAssign();
        }
    }

    private void setToManualAssign() {
        manualAssign = true;
        assignBox.setEnabled(false);
        manualAssignPanel.setVisible(true);
    }

    private boolean emptyAssign() {
        return developerMap == null || (developerMap.size() == 1 && developerMap.containsKey(PluginConstant.PLEASE_MANUAL_ASSIGN));
    }

    private class ManualFieldInput implements DocumentListener {

        @Override
        public void insertUpdate(DocumentEvent e) {
            updatePhone(e);
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            updatePhone(e);
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            updatePhone(e);
        }

        private void updatePhone(DocumentEvent e) {
            Document document = e.getDocument();
            try {
                String name = document.getText(0, document.getLength());
                String phone = getPhoneByName(name);
                manualPhoneField.setText(phone);
            } catch (BadLocationException badLocationException) {
                badLocationException.printStackTrace();
            }
        }
    }
}
