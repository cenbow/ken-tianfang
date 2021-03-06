package com.tianfang.order.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SportMBrandExample implements Serializable  {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table sport_m_brand
     *
     * @mbggenerated Wed Jan 06 09:43:02 CST 2016
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table sport_m_brand
     *
     * @mbggenerated Wed Jan 06 09:43:02 CST 2016
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table sport_m_brand
     *
     * @mbggenerated Wed Jan 06 09:43:02 CST 2016
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport_m_brand
     *
     * @mbggenerated Wed Jan 06 09:43:02 CST 2016
     */
    public SportMBrandExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport_m_brand
     *
     * @mbggenerated Wed Jan 06 09:43:02 CST 2016
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport_m_brand
     *
     * @mbggenerated Wed Jan 06 09:43:02 CST 2016
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport_m_brand
     *
     * @mbggenerated Wed Jan 06 09:43:02 CST 2016
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport_m_brand
     *
     * @mbggenerated Wed Jan 06 09:43:02 CST 2016
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport_m_brand
     *
     * @mbggenerated Wed Jan 06 09:43:02 CST 2016
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport_m_brand
     *
     * @mbggenerated Wed Jan 06 09:43:02 CST 2016
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport_m_brand
     *
     * @mbggenerated Wed Jan 06 09:43:02 CST 2016
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport_m_brand
     *
     * @mbggenerated Wed Jan 06 09:43:02 CST 2016
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport_m_brand
     *
     * @mbggenerated Wed Jan 06 09:43:02 CST 2016
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport_m_brand
     *
     * @mbggenerated Wed Jan 06 09:43:02 CST 2016
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table sport_m_brand
     *
     * @mbggenerated Wed Jan 06 09:43:02 CST 2016
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(String value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(String value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(String value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(String value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLike(String value) {
            addCriterion("id like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotLike(String value) {
            addCriterion("id not like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<String> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<String> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(String value1, String value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(String value1, String value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andBrandNameIsNull() {
            addCriterion("brand_name is null");
            return (Criteria) this;
        }

        public Criteria andBrandNameIsNotNull() {
            addCriterion("brand_name is not null");
            return (Criteria) this;
        }

        public Criteria andBrandNameEqualTo(String value) {
            addCriterion("brand_name =", value, "brandName");
            return (Criteria) this;
        }

        public Criteria andBrandNameNotEqualTo(String value) {
            addCriterion("brand_name <>", value, "brandName");
            return (Criteria) this;
        }

        public Criteria andBrandNameGreaterThan(String value) {
            addCriterion("brand_name >", value, "brandName");
            return (Criteria) this;
        }

        public Criteria andBrandNameGreaterThanOrEqualTo(String value) {
            addCriterion("brand_name >=", value, "brandName");
            return (Criteria) this;
        }

        public Criteria andBrandNameLessThan(String value) {
            addCriterion("brand_name <", value, "brandName");
            return (Criteria) this;
        }

        public Criteria andBrandNameLessThanOrEqualTo(String value) {
            addCriterion("brand_name <=", value, "brandName");
            return (Criteria) this;
        }

        public Criteria andBrandNameLike(String value) {
            addCriterion("brand_name like", value, "brandName");
            return (Criteria) this;
        }

        public Criteria andBrandNameNotLike(String value) {
            addCriterion("brand_name not like", value, "brandName");
            return (Criteria) this;
        }

        public Criteria andBrandNameIn(List<String> values) {
            addCriterion("brand_name in", values, "brandName");
            return (Criteria) this;
        }

        public Criteria andBrandNameNotIn(List<String> values) {
            addCriterion("brand_name not in", values, "brandName");
            return (Criteria) this;
        }

        public Criteria andBrandNameBetween(String value1, String value2) {
            addCriterion("brand_name between", value1, value2, "brandName");
            return (Criteria) this;
        }

        public Criteria andBrandNameNotBetween(String value1, String value2) {
            addCriterion("brand_name not between", value1, value2, "brandName");
            return (Criteria) this;
        }

        public Criteria andBrandPicIsNull() {
            addCriterion("brand_pic is null");
            return (Criteria) this;
        }

        public Criteria andBrandPicIsNotNull() {
            addCriterion("brand_pic is not null");
            return (Criteria) this;
        }

        public Criteria andBrandPicEqualTo(String value) {
            addCriterion("brand_pic =", value, "brandPic");
            return (Criteria) this;
        }

        public Criteria andBrandPicNotEqualTo(String value) {
            addCriterion("brand_pic <>", value, "brandPic");
            return (Criteria) this;
        }

        public Criteria andBrandPicGreaterThan(String value) {
            addCriterion("brand_pic >", value, "brandPic");
            return (Criteria) this;
        }

        public Criteria andBrandPicGreaterThanOrEqualTo(String value) {
            addCriterion("brand_pic >=", value, "brandPic");
            return (Criteria) this;
        }

        public Criteria andBrandPicLessThan(String value) {
            addCriterion("brand_pic <", value, "brandPic");
            return (Criteria) this;
        }

        public Criteria andBrandPicLessThanOrEqualTo(String value) {
            addCriterion("brand_pic <=", value, "brandPic");
            return (Criteria) this;
        }

        public Criteria andBrandPicLike(String value) {
            addCriterion("brand_pic like", value, "brandPic");
            return (Criteria) this;
        }

        public Criteria andBrandPicNotLike(String value) {
            addCriterion("brand_pic not like", value, "brandPic");
            return (Criteria) this;
        }

        public Criteria andBrandPicIn(List<String> values) {
            addCriterion("brand_pic in", values, "brandPic");
            return (Criteria) this;
        }

        public Criteria andBrandPicNotIn(List<String> values) {
            addCriterion("brand_pic not in", values, "brandPic");
            return (Criteria) this;
        }

        public Criteria andBrandPicBetween(String value1, String value2) {
            addCriterion("brand_pic between", value1, value2, "brandPic");
            return (Criteria) this;
        }

        public Criteria andBrandPicNotBetween(String value1, String value2) {
            addCriterion("brand_pic not between", value1, value2, "brandPic");
            return (Criteria) this;
        }

        public Criteria andBrandUrlIsNull() {
            addCriterion("brand_url is null");
            return (Criteria) this;
        }

        public Criteria andBrandUrlIsNotNull() {
            addCriterion("brand_url is not null");
            return (Criteria) this;
        }

        public Criteria andBrandUrlEqualTo(String value) {
            addCriterion("brand_url =", value, "brandUrl");
            return (Criteria) this;
        }

        public Criteria andBrandUrlNotEqualTo(String value) {
            addCriterion("brand_url <>", value, "brandUrl");
            return (Criteria) this;
        }

        public Criteria andBrandUrlGreaterThan(String value) {
            addCriterion("brand_url >", value, "brandUrl");
            return (Criteria) this;
        }

        public Criteria andBrandUrlGreaterThanOrEqualTo(String value) {
            addCriterion("brand_url >=", value, "brandUrl");
            return (Criteria) this;
        }

        public Criteria andBrandUrlLessThan(String value) {
            addCriterion("brand_url <", value, "brandUrl");
            return (Criteria) this;
        }

        public Criteria andBrandUrlLessThanOrEqualTo(String value) {
            addCriterion("brand_url <=", value, "brandUrl");
            return (Criteria) this;
        }

        public Criteria andBrandUrlLike(String value) {
            addCriterion("brand_url like", value, "brandUrl");
            return (Criteria) this;
        }

        public Criteria andBrandUrlNotLike(String value) {
            addCriterion("brand_url not like", value, "brandUrl");
            return (Criteria) this;
        }

        public Criteria andBrandUrlIn(List<String> values) {
            addCriterion("brand_url in", values, "brandUrl");
            return (Criteria) this;
        }

        public Criteria andBrandUrlNotIn(List<String> values) {
            addCriterion("brand_url not in", values, "brandUrl");
            return (Criteria) this;
        }

        public Criteria andBrandUrlBetween(String value1, String value2) {
            addCriterion("brand_url between", value1, value2, "brandUrl");
            return (Criteria) this;
        }

        public Criteria andBrandUrlNotBetween(String value1, String value2) {
            addCriterion("brand_url not between", value1, value2, "brandUrl");
            return (Criteria) this;
        }

        public Criteria andBrandOrderIsNull() {
            addCriterion("brand_order is null");
            return (Criteria) this;
        }

        public Criteria andBrandOrderIsNotNull() {
            addCriterion("brand_order is not null");
            return (Criteria) this;
        }

        public Criteria andBrandOrderEqualTo(Integer value) {
            addCriterion("brand_order =", value, "brandOrder");
            return (Criteria) this;
        }

        public Criteria andBrandOrderNotEqualTo(Integer value) {
            addCriterion("brand_order <>", value, "brandOrder");
            return (Criteria) this;
        }

        public Criteria andBrandOrderGreaterThan(Integer value) {
            addCriterion("brand_order >", value, "brandOrder");
            return (Criteria) this;
        }

        public Criteria andBrandOrderGreaterThanOrEqualTo(Integer value) {
            addCriterion("brand_order >=", value, "brandOrder");
            return (Criteria) this;
        }

        public Criteria andBrandOrderLessThan(Integer value) {
            addCriterion("brand_order <", value, "brandOrder");
            return (Criteria) this;
        }

        public Criteria andBrandOrderLessThanOrEqualTo(Integer value) {
            addCriterion("brand_order <=", value, "brandOrder");
            return (Criteria) this;
        }

        public Criteria andBrandOrderIn(List<Integer> values) {
            addCriterion("brand_order in", values, "brandOrder");
            return (Criteria) this;
        }

        public Criteria andBrandOrderNotIn(List<Integer> values) {
            addCriterion("brand_order not in", values, "brandOrder");
            return (Criteria) this;
        }

        public Criteria andBrandOrderBetween(Integer value1, Integer value2) {
            addCriterion("brand_order between", value1, value2, "brandOrder");
            return (Criteria) this;
        }

        public Criteria andBrandOrderNotBetween(Integer value1, Integer value2) {
            addCriterion("brand_order not between", value1, value2, "brandOrder");
            return (Criteria) this;
        }

        public Criteria andIsShowIsNull() {
            addCriterion("is_show is null");
            return (Criteria) this;
        }

        public Criteria andIsShowIsNotNull() {
            addCriterion("is_show is not null");
            return (Criteria) this;
        }

        public Criteria andIsShowEqualTo(Integer value) {
            addCriterion("is_show =", value, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsShowNotEqualTo(Integer value) {
            addCriterion("is_show <>", value, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsShowGreaterThan(Integer value) {
            addCriterion("is_show >", value, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsShowGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_show >=", value, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsShowLessThan(Integer value) {
            addCriterion("is_show <", value, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsShowLessThanOrEqualTo(Integer value) {
            addCriterion("is_show <=", value, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsShowIn(List<Integer> values) {
            addCriterion("is_show in", values, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsShowNotIn(List<Integer> values) {
            addCriterion("is_show not in", values, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsShowBetween(Integer value1, Integer value2) {
            addCriterion("is_show between", value1, value2, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsShowNotBetween(Integer value1, Integer value2) {
            addCriterion("is_show not between", value1, value2, "isShow");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeIsNull() {
            addCriterion("last_update_time is null");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeIsNotNull() {
            addCriterion("last_update_time is not null");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeEqualTo(Date value) {
            addCriterion("last_update_time =", value, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeNotEqualTo(Date value) {
            addCriterion("last_update_time <>", value, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeGreaterThan(Date value) {
            addCriterion("last_update_time >", value, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("last_update_time >=", value, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeLessThan(Date value) {
            addCriterion("last_update_time <", value, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("last_update_time <=", value, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeIn(List<Date> values) {
            addCriterion("last_update_time in", values, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeNotIn(List<Date> values) {
            addCriterion("last_update_time not in", values, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("last_update_time between", value1, value2, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("last_update_time not between", value1, value2, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andStatIsNull() {
            addCriterion("stat is null");
            return (Criteria) this;
        }

        public Criteria andStatIsNotNull() {
            addCriterion("stat is not null");
            return (Criteria) this;
        }

        public Criteria andStatEqualTo(Integer value) {
            addCriterion("stat =", value, "stat");
            return (Criteria) this;
        }

        public Criteria andStatNotEqualTo(Integer value) {
            addCriterion("stat <>", value, "stat");
            return (Criteria) this;
        }

        public Criteria andStatGreaterThan(Integer value) {
            addCriterion("stat >", value, "stat");
            return (Criteria) this;
        }

        public Criteria andStatGreaterThanOrEqualTo(Integer value) {
            addCriterion("stat >=", value, "stat");
            return (Criteria) this;
        }

        public Criteria andStatLessThan(Integer value) {
            addCriterion("stat <", value, "stat");
            return (Criteria) this;
        }

        public Criteria andStatLessThanOrEqualTo(Integer value) {
            addCriterion("stat <=", value, "stat");
            return (Criteria) this;
        }

        public Criteria andStatIn(List<Integer> values) {
            addCriterion("stat in", values, "stat");
            return (Criteria) this;
        }

        public Criteria andStatNotIn(List<Integer> values) {
            addCriterion("stat not in", values, "stat");
            return (Criteria) this;
        }

        public Criteria andStatBetween(Integer value1, Integer value2) {
            addCriterion("stat between", value1, value2, "stat");
            return (Criteria) this;
        }

        public Criteria andStatNotBetween(Integer value1, Integer value2) {
            addCriterion("stat not between", value1, value2, "stat");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table sport_m_brand
     *
     * @mbggenerated do_not_delete_during_merge Wed Jan 06 09:43:02 CST 2016
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table sport_m_brand
     *
     * @mbggenerated Wed Jan 06 09:43:02 CST 2016
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}