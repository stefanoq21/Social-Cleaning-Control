/*
 *  Copyright 2024 stefanoq21
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *
 */

package com.stefanoq21.socialcleaningcontrol.domain.utils

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import com.stefanoq21.socialcleaningcontrol.R
import java.io.File

class MyFileProvider : FileProvider(
    R.xml.file_paths
) {
    companion object {
        fun getImageUri(context: Context, fileName: String): Uri {

            val directory = File(context.cacheDir, "images")
            directory.mkdirs()

            val file = File.createTempFile(
                fileName,
                ".jpg",
                directory
            )

            val authority = context.packageName + ".fileprovider"

            return getUriForFile(
                context,
                authority,
                file,
            )
        }
    }
}