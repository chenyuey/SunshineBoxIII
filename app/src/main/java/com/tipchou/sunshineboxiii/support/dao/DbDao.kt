package com.tipchou.sunshineboxiii.support.dao

import com.tipchou.sunshineboxiii.entity.local.*
import com.tipchou.sunshineboxiii.support.App
import com.tipchou.sunshineboxiii.support.LessonType
import io.objectbox.Box
import io.objectbox.BoxStore
import io.objectbox.android.ObjectBoxLiveData
import io.objectbox.query.QueryBuilder
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by 邵励治 on 2018/4/26.
 * Perfect Code
 */
@Singleton
class DbDao @Inject constructor() {

    private val boxStore: BoxStore? by lazy { App.getBoxStore() }

    fun getTest(): ObjectBoxLiveData<TestLocal> {
        if (boxStore == null) {
            //should not be here!!!!!!
            throw Exception("App.getBoxStore() get null!!!")
        } else {
            val userBox: Box<TestLocal> = boxStore!!.boxFor(TestLocal::class.java)
            return ObjectBoxLiveData(userBox.query().order(TestLocal_.userId).build())
        }
    }

    fun saveTest(testLocalList: List<TestLocal>) {
        if (boxStore == null) {
            //should not be here!!!!!!
            throw Exception("App.getBoxStore() get null!!!")
        } else {
            val userBox = boxStore!!.boxFor(TestLocal::class.java)
            userBox.put(testLocalList)
        }
    }

    fun removeTest(testLocalList: List<TestLocal>) {
        if (boxStore == null) {
            //should not be here!!!!!!
            throw Exception("App.getBoxStore() get null!!!")
        } else {
            val userBox = boxStore!!.boxFor(TestLocal::class.java)
            userBox.remove(testLocalList)
        }
    }

    fun getRole(): ObjectBoxLiveData<RoleLocal> {
        if (boxStore == null) {
            //should not be here!!!!!!
            throw Exception("App.getBoxStore() get null!!!")
        } else {
            val roleBox: Box<RoleLocal> = boxStore!!.boxFor(RoleLocal::class.java)
            return ObjectBoxLiveData(roleBox.query().order(RoleLocal_.id).build())
        }
    }

    fun saveRole(roleLocaleList: List<RoleLocal>) {
        if (boxStore == null) {
            //should not be here!!!!!!
            throw Exception("App.getBoxStore() get null!!!")
        } else {
            val roleBox = boxStore!!.boxFor(RoleLocal::class.java)
            roleBox.put(roleLocaleList)
        }
    }

    fun removeRole(roleLocaleList: List<RoleLocal>) {
        if (boxStore == null) {
            //should not be here!!!!!!
            throw Exception("App.getBoxStore() get null!!!")
        } else {
            val roleBox = boxStore!!.boxFor(RoleLocal::class.java)
            roleBox.remove(roleLocaleList)
        }
    }

    fun getLesson(lessonType: LessonType): ObjectBoxLiveData<LessonLocal> {
        if (boxStore == null) {
            //should not be here!!!!!!
            throw Exception("App.getBoxStore() get null!!!")
        } else {
            val lessonBox: Box<LessonLocal> = boxStore!!.boxFor(LessonLocal::class.java)
            val queryBuilder: QueryBuilder<LessonLocal>? = lessonBox.query()
            if (queryBuilder == null) {
                //should not be here!!
                throw Exception("queryBuilder is null!!!")
            } else {
                when (lessonType) {
                    LessonType.NURSERY -> queryBuilder.equal(LessonLocal_.subject, "NURSERY")
                    LessonType.MUSIC -> queryBuilder.equal(LessonLocal_.subject, "MUSIC")
                    LessonType.READING -> queryBuilder.equal(LessonLocal_.subject, "READING")
                    LessonType.GAME -> queryBuilder.equal(LessonLocal_.subject, "GAME")
                    LessonType.HEALTH -> queryBuilder.contains(LessonLocal_.tags, "domain.健康")
                    LessonType.LANGUAGE -> queryBuilder.contains(LessonLocal_.tags, "domain.语言")
                    LessonType.SOCIAL -> queryBuilder.contains(LessonLocal_.tags, "domain.社会")
                    LessonType.SCIENCE -> queryBuilder.contains(LessonLocal_.tags, "domain.科学")
                    LessonType.ART -> queryBuilder.contains(LessonLocal_.tags, "domain.艺术")
                }
                return ObjectBoxLiveData(queryBuilder.build())
            }
        }
    }

    fun saveLesson(lessonLocalList: List<LessonLocal>) {
        if (boxStore == null) {
            //should not be here!!!!!!
            throw Exception("App.getBoxStore() get null!!!")
        } else {
            val lessonBox = boxStore!!.boxFor(LessonLocal::class.java)
            lessonBox.put(lessonLocalList)
        }
    }

    fun removeLesson(lessonLocalList: List<LessonLocal>) {
        if (boxStore == null) {
            //should not be here!!!!!!
            throw Exception("App.getBoxStore() get null!!!")
        } else {
            val lessonBox = boxStore!!.boxFor(LessonLocal::class.java)
            lessonBox.remove(lessonLocalList)
        }
    }

    fun getLesson(lessonObjectIdList: List<String>): ArrayList<LessonLocal> {
        if (boxStore == null) {
            //should not be here!!!!!!
            throw Exception("App.getBoxStore() get null!!!")
        } else {
            val lessonBox: Box<LessonLocal> = boxStore!!.boxFor(LessonLocal::class.java)
            val lessonList = arrayListOf<LessonLocal>()
            for (lessonObjectId in lessonObjectIdList) {
                val queryBuilder: QueryBuilder<LessonLocal>? = lessonBox.query()
                if (queryBuilder == null) {
                    //should not be here!!
                    throw Exception("queryBuilder is null!!!")
                } else {
                    val lessonLocal = queryBuilder.equal(LessonLocal_.objectId, lessonObjectId).build().find()
                    if (lessonLocal.size == 1) {
                        lessonList.add(lessonLocal[0])
                    }
                }
            }
            return lessonList
        }
    }

    fun getDownload(): ObjectBoxLiveData<DownloadLocal> {
        if (boxStore == null) {
            //should not be here!!!!!!
            throw Exception("App.getBoxStore() get null!!!")
        } else {
            val downloadBox: Box<DownloadLocal> = boxStore!!.boxFor(DownloadLocal::class.java)
            val queryBuilder: QueryBuilder<DownloadLocal>? = downloadBox.query()
            if (queryBuilder == null) {
                //should not be here
                throw Exception("queryBuilder is null!!!")
            } else {
                return ObjectBoxLiveData(queryBuilder.build())
            }
        }
    }

    fun saveDownload(objectId: String, publishedUrl: String?, stagingUrl: String?) {
        if (boxStore == null) {
            //should not be here!!!!!!
            throw Exception("App.getBoxStore() get null!!!")
        } else {
            val downloadBox: Box<DownloadLocal> = boxStore!!.boxFor(DownloadLocal::class.java)
            //1.查询该ObjectId是否存在
            val result: List<DownloadLocal> = downloadBox.query().equal(DownloadLocal_.objectId, objectId).build().find()
            if (result.isNotEmpty()) {
                //若该objectId存在
                if (result.size != 1) {
                    //should not be here
                    throw Exception("DbDao: saveDownload()'s objectId is not unique!!!")
                } else {
                    val downloadLocal: DownloadLocal = result[0]
                    downloadLocal.publishedUrl = publishedUrl
                    downloadLocal.stagingUrl = stagingUrl
                    downloadBox.put(downloadLocal)
                }
            } else {
                //若该objectId不存在
                val downloadLocal = DownloadLocal(objectId = objectId, publishedUrl = publishedUrl, stagingUrl = stagingUrl)
                downloadBox.put(downloadLocal)
            }
        }
    }

    fun removeDownload() {
        if (boxStore == null) {
            //should not be here!!!!!!
            throw Exception("App.getBoxStore() get null!!!")
        } else {
            val downloadBox: Box<DownloadLocal> = boxStore!!.boxFor(DownloadLocal::class.java)
            downloadBox.removeAll()
        }
    }

    fun getFavorite(): ObjectBoxLiveData<FavoriteLocal> {
        if (boxStore == null) {
            //should not be here!!!!!!
            throw Exception("App.getBoxStore() get null!!!")
        } else {
            val favoriteBox: Box<FavoriteLocal> = boxStore!!.boxFor(FavoriteLocal::class.java)
            return ObjectBoxLiveData(favoriteBox.query().build())
        }
    }

    fun saveFavorite(favoriteList: List<FavoriteLocal>) {
        if (boxStore == null) {
            //should not be here!!!!!!
            throw Exception("App.getBoxStore() get null!!!")
        } else {
            val favoriteBox = boxStore!!.boxFor(FavoriteLocal::class.java)
            favoriteBox.put(favoriteList)
        }
    }

    fun removeFavorite(favoriteList: List<FavoriteLocal>) {
        if (boxStore == null) {
            //should not be here!!!!!!
            throw Exception("App.getBoxStore() get null!!!")
        } else {
            val favoriteBox = boxStore!!.boxFor(FavoriteLocal::class.java)
            favoriteBox.remove(favoriteList)
        }
    }

    fun getSpecialSubject(): ObjectBoxLiveData<SpecialSubjectLocal> {
        if (boxStore == null) {
            //should not be here!!!!!!
            throw Exception("App.getBoxStore() get null!!!")
        } else {
            val specialSubjectBox = boxStore!!.boxFor(SpecialSubjectLocal::class.java)
            return ObjectBoxLiveData(specialSubjectBox.query().build())
        }
    }

    fun saveSpecialSubject(specialSubjectLocalList: List<SpecialSubjectLocal>) {
        if (boxStore == null) {
            //should not be here!!!!!!
            throw Exception("App.getBoxStore() get null!!!")
        } else {
            val specialSubjectBox = boxStore!!.boxFor(SpecialSubjectLocal::class.java)
            specialSubjectBox.put(specialSubjectLocalList)
        }
    }

    fun removeSpecialSubject(specialSubjectLocalList: List<SpecialSubjectLocal>) {
        if (boxStore == null) {
            //should not be here!!!!!!
            throw Exception("App.getBoxStore() get null!!!")
        } else {
            val specialSubjectBox = boxStore!!.boxFor(SpecialSubjectLocal::class.java)
            specialSubjectBox.remove(specialSubjectLocalList)
        }
    }


    fun getLessonSubject(specialObjectId: String): ObjectBoxLiveData<LessonSubjectLocal> {
        if (boxStore == null) {
            //should not be here!!!!!!
            throw Exception("App.getBoxStore() get null!!!")
        } else {
            val lessonSubjectBox = boxStore!!.boxFor(LessonSubjectLocal::class.java)
            val queryBuilder: QueryBuilder<LessonSubjectLocal>? = lessonSubjectBox.query()
            if (queryBuilder == null) {
                //should not be here!!
                throw Exception("queryBuilder is null!!!")
            } else {
                queryBuilder.equal(LessonSubjectLocal_.specialObjectId, specialObjectId)
                return ObjectBoxLiveData(queryBuilder.build())
            }
        }
    }

    fun saveLessonSubject(lessonSubjectLocalList: List<LessonSubjectLocal>) {
        if (boxStore == null) {
            //should not be here!!!!!!
            throw Exception("App.getBoxStore() get null!!!")
        } else {
            val lessonSubjectBox = boxStore!!.boxFor(LessonSubjectLocal::class.java)
            lessonSubjectBox.put(lessonSubjectLocalList)
        }
    }

    fun removeLessonSubject(lessonSubjectLocalList: List<LessonSubjectLocal>) {
        if (boxStore == null) {
            //should not be here!!!!!!
            throw Exception("App.getBoxStore() get null!!!")
        } else {
            val lessonSubjectBox = boxStore!!.boxFor(LessonSubjectLocal::class.java)
            lessonSubjectBox.remove(lessonSubjectLocalList)
        }
    }

    fun removeAll() {
        if (boxStore == null) {
            //should not be here!!!!!!
            throw Exception("App.getBoxStore() get null!!!")
        } else {
            val downloadBox = boxStore!!.boxFor(DownloadLocal::class.java)
            val favoriteBox = boxStore!!.boxFor(FavoriteLocal::class.java)
            val lessonBox = boxStore!!.boxFor(LessonLocal::class.java)
            val roleBox = boxStore!!.boxFor(RoleLocal::class.java)
            val specialSubjectBox = boxStore!!.boxFor(SpecialSubjectLocal::class.java)

            downloadBox.removeAll()
            favoriteBox.removeAll()
            lessonBox.removeAll()
            roleBox.removeAll()
            specialSubjectBox.removeAll()
        }
    }
}
