package org.kuska.bscapp.util.rx;

import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author Petr Kuška (kuska.petr@gmail.com)
 * @since 2018/06/24
 */
public interface AppSchedulers {
    AppSchedulers DEFAULT = new AppSchedulers() {
        @Override
        public Scheduler ui() {
            return AndroidSchedulers.mainThread();
        }

        @Override
        public Scheduler background() {
            return Schedulers.io();
        }

        @Override
        public Scheduler current() {
            return Schedulers.immediate();
        }

    };

    /**
     * @return Scheduler that performs on main application thread. It is save to use this scheduler
     * for all UI related tasks.
     */
    rx.Scheduler ui();

    /**
     * @return Scheduler that performs on background thread. Suitable for all long running and intensive tasks.
     */
    rx.Scheduler background();

    /**
     * @return Scheduler that performs on caller's current thread.
     */
    rx.Scheduler current();
}
