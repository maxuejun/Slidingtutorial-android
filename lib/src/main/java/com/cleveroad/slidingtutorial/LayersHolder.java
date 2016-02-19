/*
 *   The MIT License (MIT)
 *
 *   Copyright (c) 2015 Cleveroad
 *
 *   Permission is hereby granted, free of charge, to any person obtaining a copy
 *   of this software and associated documentation files (the "Software"), to deal
 *   in the Software without restriction, including without limitation the rights
 *   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *   copies of the Software, and to permit persons to whom the Software is
 *   furnished to do so, subject to the following conditions:
 *
 *   The above copyright notice and this permission notice shall be included in all
 *   copies or substantial portions of the Software.
 *
 *   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *   SOFTWARE.
 */
package com.cleveroad.slidingtutorial;

import android.view.View;

/**
 * {@link TransformItem}'s holder, that implement a custom transformation
 * to the page views using animation properties.
 */
class LayersHolder {
	TransformItem[] transformItems;

	public LayersHolder(View view, TransformItem[] transformItems) {
		this.transformItems = transformItems;

		for (TransformItem transformItem : transformItems) {
			transformItem.setView(view.findViewById(transformItem.getViewResId()));
		}
	}

	/**
	 * Method that apply a custom transformation to the page views
	 *
	 * @param pageWidth pageWidth
	 * @param position  Position of page relative to the current front-and-center
	 *                  position of the pager. 0 is front and center. 1 is one full
	 *                  page position to the right, and -1 is one page position to the left.
	 */
	public void transformPage(int pageWidth, float position) {
		for (TransformItem transformItem : transformItems) {
			float translationX = (position) * (pageWidth / transformItem.getShiftCoefficient());

			transformItem.getView().setTranslationX(transformItem.isReverseShift() ? -translationX : translationX);
		}
	}
}
