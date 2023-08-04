package com.vickikbt.shared.ui.components.collapsing_toolbar

@RequiresOptIn(
	message = "This is an experimental API of compose-collapsing-toolbar. Any declarations with " +
			"the annotation might be removed or changed in some way without any notice.",
	level = RequiresOptIn.Level.WARNING
)
@Target(
	AnnotationTarget.FUNCTION,
	AnnotationTarget.PROPERTY,
	AnnotationTarget.CLASS
)
@Retention(AnnotationRetention.BINARY)
annotation class ExperimentalToolbarApi