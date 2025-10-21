package com.keyboardtest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.util.AttributeSet;
import java.util.List;

// هذا هو الحل الجذري: نسخة مبسطة ومستقرة تعتمد على نظام أندرويد الافتراضي
public class MyKeyboardView extends KeyboardView {
	
	// لا حاجة لتعديل هذه الدوال
	public MyKeyboardView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	public MyKeyboardView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	// تم حذف كل الإضافات المعقدة (مثل onLongPress) التي كانت تسبب المشكلة
	// الآن سيعتمد التطبيق على السلوك الافتراضي والمستقر لنظام أندرويد لعرض نافذة الخيارات
	
	@Override
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		// هذا الكود آمن ومسؤول فقط عن رسم الرموز الصغيرة على المفاتيح
		Paint paint = new Paint();
		paint.setTextAlign(Paint.Align.CENTER);
		paint.setTextSize(40);
		paint.setColor(Color.LTGRAY);
		
		List<Keyboard.Key> keys = getKeyboard().getKeys();
		for (Keyboard.Key key : keys) {
			if (key.popupCharacters != null && key.popupCharacters.length() > 0) {
				String hint = key.popupCharacters.toString().substring(0, 1);
				canvas.drawText(
				hint,
				key.x + (key.width * 0.85f),
				key.y + (key.height * 0.40f),
				paint
				);
			}
		}
	}
}