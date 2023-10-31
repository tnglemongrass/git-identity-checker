package com.yourplugin

import com.intellij.openapi.components.ApplicationComponent
import com.intellij.openapi.diagnostic.Logger

class NoCommitsAfterSixComponent : ApplicationComponent {
    companion object {
        private val LOG: Logger = Logger.getInstance(NoCommitsAfterSixComponent::class.java)
    }

    @Deprecated("Deprecated in Java")
    override fun initComponent() {
        LOG.info("asdf - NoCommitsAfterSix plugin has been loaded successfully")
    }

    @Deprecated("Deprecated in Java")
    override fun disposeComponent() {
        // add any necessary cleanup here
    }

    override fun getComponentName(): String {
        return "NoCommitsAfterSixComponent"
    }
}
