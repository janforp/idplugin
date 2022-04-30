package com.janita.idplugin.woodpecker.common.domain;

/**
 * CurrentProgress
 *
 * @author zhucj
 * @since 20220324
 */
public class CurrentProgress {

    private long already;

    private long total;

    public long getAlready() {
        return already;
    }

    public long getTotal() {
        return total;
    }

    public void setAlready(long already) {
        this.already = already;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
