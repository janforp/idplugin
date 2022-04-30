package com.janita.idplugin.woodpecker.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * CrQuestionState
 *
 * @author zhucj
 * @since 20220324
 */
@AllArgsConstructor
public enum CrQuestionState {

    UNSOLVED("未解决"),

    SOLVED("已解决"),

    REJECT("不予解决"),

    DUPLICATE("重复问题"),

    CLOSED("已关闭"),
    ;

    public static final String TOTAL = "全部";

    @Getter
    private final String desc;

    public static CrQuestionState getByDesc(String desc) {
        for (CrQuestionState state : CrQuestionState.values()) {
            if (state.getDesc().equals(desc)) {
                return state;
            }
        }
        return UNSOLVED;
    }

    public static CrQuestionState getByDescOrReturnNull(String desc) {
        for (CrQuestionState state : CrQuestionState.values()) {
            if (state.getDesc().equals(desc)) {
                return state;
            }
        }
        return null;
    }

    public static String[] getDescArray() {
        String[] arr = new String[CrQuestionState.values().length];
        for (int i = 0; i < CrQuestionState.values().length; i++) {
            arr[i] = CrQuestionState.values()[i].getDesc();
        }
        return arr;
    }
}
