package com.yfbx.mybrowser.activity

import android.os.Bundle
import com.yfbx.mybrowser.R

/**
 * Author: Edward
 * Date: 2019/1/4
 * Description:
 */

class FileActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file)
        setToolbar(R.string.download)

    }
}