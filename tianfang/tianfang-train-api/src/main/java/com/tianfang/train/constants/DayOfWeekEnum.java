package com.tianfang.train.constants;

public enum DayOfWeekEnum {
	Monday(1,"周一"),
	Tuesday(2,"周二"),
	Wednesday(3,"周三"),
	Thursday(4,"周四"),
	Friday(5,"周五"),
	Saturday(6,"周六"),
	Sunday(7,"周日");
	
	DayOfWeekEnum(int index,String value){
		this.index =index;
		this.value =value;
	}
	
	private int index;
	
	private String value;
	/**
	 * 根据index获取value
	 * @param index
	 * @return
	 */
	public static String getByIndexValue(int index){
		for (DayOfWeekEnum d : DayOfWeekEnum.values()) {
			if (d.getIndex() == index) {
				return d.value;
			}
		}
		return null;
	}
	/**
	 * 根据value获取id
	 * @param value
	 * @return
	 */
	public static int getByValueIndex(String value){
		for (DayOfWeekEnum d : DayOfWeekEnum.values()) {
			if(d.getValue().equals(value)){
				return d.getIndex();
			}
		}
		return 0;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
}
