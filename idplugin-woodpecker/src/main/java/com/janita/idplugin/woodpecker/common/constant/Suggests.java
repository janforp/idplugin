package com.janita.idplugin.woodpecker.common.constant;

import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.List;

/**
 * Suggests
 *
 * @author zhucj
 * @since 20220324
 */
@UtilityClass
public class Suggests {

    private static final List<String> SUGGEST = Arrays.asList(
            "列表选择状态可以多选，全部",
            "编辑界面显示代码高亮",
            "每次打开列表触发查询",
            "切换项目跟状态，自动触发查询",
            "列表按创建人搜索",
            "添加按提问迭代分支搜索"
    );
}