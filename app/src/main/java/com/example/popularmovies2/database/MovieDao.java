package com.example.popularmovies2.database;



import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.popularmovies2.model.Movie;

import java.util.List;


@Dao
public interface MovieDao {

    @Query("SELECT * FROM movie ORDER BY vote_average DESC")
    LiveData<List<MovieEntry>> loadAllMovies();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovie(MovieEntry movie);

    @Delete
    void deleteMovie(MovieEntry movie);

    @Query("SELECT * FROM movie WHERE movie_id = :movieId")
    LiveData<MovieEntry> loadMovieByMovieId(int movieId);
}
