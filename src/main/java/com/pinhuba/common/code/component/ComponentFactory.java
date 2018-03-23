package com.pinhuba.common.code.component;

public class ComponentFactory {
	
	public static Component getComponent(int type) {
		switch (type) {
		case Component.NICEFORM:
			return new NiceForm();
		case Component.NUMFORM:
			return new NumForm();
		case Component.RMBFORM:
			return new RmbForm();
		case Component.DATEFORM:
			return new DateForm();
		case Component.SELECT:
			return new Select();
		case Component.RADIO:
			return new Radio();
		case Component.CHECKBOX:
			return new Checkbox();
		case Component.TAKEFORM_TEXT:
			return new TakeFormText();
		case Component.TAKEFORM_TEXTAREA:
			return new TakeFormTextarea();
		case Component.TEXTAREA:
			return new Textarea();
		case Component.RICHTEXTAREA:
			return new RichTextarea();
		case Component.UPLOADIMG:
			return new UploadImg();
		case Component.UPLOADFILE:
			return new UploadFile();
		default:
			break;
		}
		return null;
	}
	
	
}
