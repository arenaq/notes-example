package org.kuska.bscapp.injection;

import org.kuska.bscapp.util.rx.AppSchedulers;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author Petr Kuška (kuska.petr@gmail.com)
 * @since 2018/06/24
 */
@Module
public class AppModule {
    @Provides
    @Singleton
    AppSchedulers provideAppSchedulers() {
        return AppSchedulers.DEFAULT;
    }
}
