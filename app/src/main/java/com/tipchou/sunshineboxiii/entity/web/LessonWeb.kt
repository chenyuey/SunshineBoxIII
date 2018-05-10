package com.tipchou.sunshineboxiii.entity.web

import com.avos.avoscloud.AVFile
import com.avos.avoscloud.AVObject

/**
 * Created by 邵励治 on 2018/5/2.
 * Perfect Code
 */
class LessonWeb constructor(avObject: AVObject) {
    val objectId: String = avObject.objectId
    val isPublish: Boolean? = avObject.getBoolean("isPublished")
    val tags: String? = avObject.getList("tags")?.toString()
    val packageUrl: String? = avObject.getAVFile<AVFile>("package")?.url
    val name: String? = avObject.getString("name")
    val versionCode: Int? = avObject.getNumber("version_code")?.toInt()
    val draftVersionCode: Int? = avObject.getNumber("draft_version_code")?.toInt()
    val subject: String? = when (avObject.getAVObject<AVObject>("subject")?.objectId) {
        "5a8e908dac502e0032b6225d" -> "GAME"
        "5a701c82d50eee00444134b2" -> "READING"
        "5a741bcb2f301e003be904ed" -> "MUSIC"
        "5a701c8c1b69e6003c534903" -> "NURSERY"
        else -> null
    }
    val compiler: String? = avObject.getString("compiler")
    val stagingPackageUrl: String? = avObject.getAVFile<AVFile>("staging_package")?.url
    val isChecked: Int? = avObject.getNumber("isChecked")?.toInt()
    override fun toString(): String {
        return "LessonWeb(isPublish=$isPublish, tags=$tags, packageUrl=$packageUrl, name=$name, versionCode=$versionCode, draftVersionCode=$draftVersionCode, subject=$subject, compiler=$compiler, stagingPackageUrl=$stagingPackageUrl, isChecked=$isChecked)"
    }
}
