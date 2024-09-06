package com.example.animalapp.data.db.dao

import androidx.test.filters.MediumTest
import app.cash.turbine.test
import com.example.animalapp.data.db.AnimalDatabase
import com.example.animalapp.data.model.local.BreedEntity
import com.example.animalapp.data.model.local.BreedWithImage
import com.example.animalapp.data.model.local.ImageEntity
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named


@HiltAndroidTest
@MediumTest
class BreedDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("test_db")
    lateinit var database: AnimalDatabase

    private lateinit var breedDao: BreedDao
    private lateinit var imageDao: ImageDao

    private val dispatcher = StandardTestDispatcher()
    private val scope = TestScope(dispatcher)

    @Before
    fun setUp() {
        hiltRule.inject()
        breedDao = database.BreedDao()
        imageDao = database.ImageDao()
    }

    @After
    fun tearDown() {
    }

    @Test
    fun checkDataIsInsertedInBreedTableInDb() = scope.runTest {
        breedDao.insert(BreedEntity.DEFAULT)
        imageDao.insert(ImageEntity.DEFAULT)

        breedDao.getUsersWithPlaylists().test {
            val result = awaitItem()
            assertThat(result[0].breed).isEqualTo(BreedEntity.DEFAULT)
            assertThat(result[0].image).isEqualTo(ImageEntity.DEFAULT)
        }
    }

    @Test
    fun checkDataIsRemovedFromImageWhenBreedIsRemoved() = scope.runTest {
        breedDao.insert(BreedEntity.DEFAULT)
        imageDao.insert(ImageEntity.DEFAULT)
        breedDao.remove(BreedEntity.DEFAULT.id)

        imageDao.getImages().test {
            assertThat(awaitItem().size).isEqualTo(0)
        }
    }
}