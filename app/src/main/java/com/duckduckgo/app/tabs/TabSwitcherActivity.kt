/*
 * Copyright (c) 2018 DuckDuckGo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.duckduckgo.app.tabs

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.duckduckgo.app.browser.R
import com.duckduckgo.app.global.DuckDuckGoActivity
import com.duckduckgo.app.global.ViewModelFactory
import kotlinx.android.synthetic.main.content_tabs.*
import kotlinx.android.synthetic.main.include_toolbar.*
import javax.inject.Inject

class TabSwitcherActivity : DuckDuckGoActivity(), TabSwitcherAdapter.OnItemClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var tabManager: TabManager

    private val viewModel: TabSwitcherViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(TabSwitcherViewModel::class.java)
    }
    private val tabsAdapter = TabSwitcherAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tabs)
        configureToolbar()
        configureRecycler()
    }

    private fun configureToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun configureRecycler() {
        tabsRecycler.layoutManager = LinearLayoutManager(this)
        tabsRecycler.adapter = tabsAdapter
        tabsAdapter.updateData(tabManager.tabs.list)
    }

    override fun onClicked(position: Int) {
        tabManager.selectTab(position)
        finish()
    }

    companion object {
        fun intent(context: Context): Intent {
            return Intent(context, TabSwitcherActivity::class.java)
        }
    }
}