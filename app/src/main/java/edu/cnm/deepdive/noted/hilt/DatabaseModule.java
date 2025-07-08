package edu.cnm.deepdive.noted.hilt;

import android.content.Context;
import androidx.room.Room;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import edu.cnm.deepdive.noted.service.NotedDatabase;
import edu.cnm.deepdive.noted.service.dao.ImageDao;
import edu.cnm.deepdive.noted.service.dao.NoteDao;
import edu.cnm.deepdive.noted.service.dao.UserDao;
import edu.cnm.deepdive.noted.service.util.Preloader;
import javax.inject.Singleton;

@Module
@InstallIn(SingletonComponent.class)
public class DatabaseModule {

  @Provides
  @Singleton
  NotedDatabase provideDatabase(@ApplicationContext Context context, Preloader preloader) {
    return Room.databaseBuilder(context, NotedDatabase.class, NotedDatabase.getName())
        .addCallback(preloader)
        .build();
  }

  @Provides
  @Singleton
  UserDao provideUserDao(NotedDatabase database) {
    return database.getUserDao();
  }

  @Provides
  @Singleton
  NoteDao provideNoteDao(NotedDatabase database) {
    return database.getNoteDao();
  }

  @Provides
  @Singleton
  ImageDao provideImageDao(NotedDatabase database) {
    return database.getImageDao();
  }



}
