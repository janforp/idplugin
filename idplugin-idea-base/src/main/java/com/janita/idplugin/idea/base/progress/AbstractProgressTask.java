package com.janita.idplugin.idea.base.progress;

import com.janita.idplugin.idea.base.domain.CurrentProgress;

/**
 * ProgressTask
 *
 * @author zhucj
 * @since 20220324
 */
public abstract class AbstractProgressTask implements ProgressTask {

    @Override
    public long totalBit() {
        return 1000000;
    }

    @Override
    public long alreadyCompleteBit() {
        return 50000;
    }

    @Override
    public long lastAlreadyCompleteBit() {
        return 10000;
    }

    @Override
    public void process(CurrentProgress progress) {
        progress.setTotal(totalBit());
        progress.setAlready(alreadyCompleteBit());
        doProcess();
        try {
            Thread.sleep(sleepSecond() * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        progress.setAlready(totalBit());
    }

    protected abstract void doProcess();
}
