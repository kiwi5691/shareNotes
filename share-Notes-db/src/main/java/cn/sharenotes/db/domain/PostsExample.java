package cn.sharenotes.db.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PostsExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PostsExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

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

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(Integer value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(Integer value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(Integer value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(Integer value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(Integer value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<Integer> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<Integer> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(Integer value1, Integer value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("type not between", value1, value2, "type");
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

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andCreateFromIsNull() {
            addCriterion("create_from is null");
            return (Criteria) this;
        }

        public Criteria andCreateFromIsNotNull() {
            addCriterion("create_from is not null");
            return (Criteria) this;
        }

        public Criteria andCreateFromEqualTo(Integer value) {
            addCriterion("create_from =", value, "createFrom");
            return (Criteria) this;
        }

        public Criteria andCreateFromNotEqualTo(Integer value) {
            addCriterion("create_from <>", value, "createFrom");
            return (Criteria) this;
        }

        public Criteria andCreateFromGreaterThan(Integer value) {
            addCriterion("create_from >", value, "createFrom");
            return (Criteria) this;
        }

        public Criteria andCreateFromGreaterThanOrEqualTo(Integer value) {
            addCriterion("create_from >=", value, "createFrom");
            return (Criteria) this;
        }

        public Criteria andCreateFromLessThan(Integer value) {
            addCriterion("create_from <", value, "createFrom");
            return (Criteria) this;
        }

        public Criteria andCreateFromLessThanOrEqualTo(Integer value) {
            addCriterion("create_from <=", value, "createFrom");
            return (Criteria) this;
        }

        public Criteria andCreateFromIn(List<Integer> values) {
            addCriterion("create_from in", values, "createFrom");
            return (Criteria) this;
        }

        public Criteria andCreateFromNotIn(List<Integer> values) {
            addCriterion("create_from not in", values, "createFrom");
            return (Criteria) this;
        }

        public Criteria andCreateFromBetween(Integer value1, Integer value2) {
            addCriterion("create_from between", value1, value2, "createFrom");
            return (Criteria) this;
        }

        public Criteria andCreateFromNotBetween(Integer value1, Integer value2) {
            addCriterion("create_from not between", value1, value2, "createFrom");
            return (Criteria) this;
        }

        public Criteria andDisallowCommentIsNull() {
            addCriterion("disallow_comment is null");
            return (Criteria) this;
        }

        public Criteria andDisallowCommentIsNotNull() {
            addCriterion("disallow_comment is not null");
            return (Criteria) this;
        }

        public Criteria andDisallowCommentEqualTo(Integer value) {
            addCriterion("disallow_comment =", value, "disallowComment");
            return (Criteria) this;
        }

        public Criteria andDisallowCommentNotEqualTo(Integer value) {
            addCriterion("disallow_comment <>", value, "disallowComment");
            return (Criteria) this;
        }

        public Criteria andDisallowCommentGreaterThan(Integer value) {
            addCriterion("disallow_comment >", value, "disallowComment");
            return (Criteria) this;
        }

        public Criteria andDisallowCommentGreaterThanOrEqualTo(Integer value) {
            addCriterion("disallow_comment >=", value, "disallowComment");
            return (Criteria) this;
        }

        public Criteria andDisallowCommentLessThan(Integer value) {
            addCriterion("disallow_comment <", value, "disallowComment");
            return (Criteria) this;
        }

        public Criteria andDisallowCommentLessThanOrEqualTo(Integer value) {
            addCriterion("disallow_comment <=", value, "disallowComment");
            return (Criteria) this;
        }

        public Criteria andDisallowCommentIn(List<Integer> values) {
            addCriterion("disallow_comment in", values, "disallowComment");
            return (Criteria) this;
        }

        public Criteria andDisallowCommentNotIn(List<Integer> values) {
            addCriterion("disallow_comment not in", values, "disallowComment");
            return (Criteria) this;
        }

        public Criteria andDisallowCommentBetween(Integer value1, Integer value2) {
            addCriterion("disallow_comment between", value1, value2, "disallowComment");
            return (Criteria) this;
        }

        public Criteria andDisallowCommentNotBetween(Integer value1, Integer value2) {
            addCriterion("disallow_comment not between", value1, value2, "disallowComment");
            return (Criteria) this;
        }

        public Criteria andEditTimeIsNull() {
            addCriterion("edit_time is null");
            return (Criteria) this;
        }

        public Criteria andEditTimeIsNotNull() {
            addCriterion("edit_time is not null");
            return (Criteria) this;
        }

        public Criteria andEditTimeEqualTo(Date value) {
            addCriterion("edit_time =", value, "editTime");
            return (Criteria) this;
        }

        public Criteria andEditTimeNotEqualTo(Date value) {
            addCriterion("edit_time <>", value, "editTime");
            return (Criteria) this;
        }

        public Criteria andEditTimeGreaterThan(Date value) {
            addCriterion("edit_time >", value, "editTime");
            return (Criteria) this;
        }

        public Criteria andEditTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("edit_time >=", value, "editTime");
            return (Criteria) this;
        }

        public Criteria andEditTimeLessThan(Date value) {
            addCriterion("edit_time <", value, "editTime");
            return (Criteria) this;
        }

        public Criteria andEditTimeLessThanOrEqualTo(Date value) {
            addCriterion("edit_time <=", value, "editTime");
            return (Criteria) this;
        }

        public Criteria andEditTimeIn(List<Date> values) {
            addCriterion("edit_time in", values, "editTime");
            return (Criteria) this;
        }

        public Criteria andEditTimeNotIn(List<Date> values) {
            addCriterion("edit_time not in", values, "editTime");
            return (Criteria) this;
        }

        public Criteria andEditTimeBetween(Date value1, Date value2) {
            addCriterion("edit_time between", value1, value2, "editTime");
            return (Criteria) this;
        }

        public Criteria andEditTimeNotBetween(Date value1, Date value2) {
            addCriterion("edit_time not between", value1, value2, "editTime");
            return (Criteria) this;
        }

        public Criteria andTitleIsNull() {
            addCriterion("title is null");
            return (Criteria) this;
        }

        public Criteria andTitleIsNotNull() {
            addCriterion("title is not null");
            return (Criteria) this;
        }

        public Criteria andTitleEqualTo(String value) {
            addCriterion("title =", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotEqualTo(String value) {
            addCriterion("title <>", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThan(String value) {
            addCriterion("title >", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThanOrEqualTo(String value) {
            addCriterion("title >=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThan(String value) {
            addCriterion("title <", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThanOrEqualTo(String value) {
            addCriterion("title <=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLike(String value) {
            addCriterion("title like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotLike(String value) {
            addCriterion("title not like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleIn(List<String> values) {
            addCriterion("title in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotIn(List<String> values) {
            addCriterion("title not in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleBetween(String value1, String value2) {
            addCriterion("title between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotBetween(String value1, String value2) {
            addCriterion("title not between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andTopPriorityIsNull() {
            addCriterion("top_priority is null");
            return (Criteria) this;
        }

        public Criteria andTopPriorityIsNotNull() {
            addCriterion("top_priority is not null");
            return (Criteria) this;
        }

        public Criteria andTopPriorityEqualTo(Integer value) {
            addCriterion("top_priority =", value, "topPriority");
            return (Criteria) this;
        }

        public Criteria andTopPriorityNotEqualTo(Integer value) {
            addCriterion("top_priority <>", value, "topPriority");
            return (Criteria) this;
        }

        public Criteria andTopPriorityGreaterThan(Integer value) {
            addCriterion("top_priority >", value, "topPriority");
            return (Criteria) this;
        }

        public Criteria andTopPriorityGreaterThanOrEqualTo(Integer value) {
            addCriterion("top_priority >=", value, "topPriority");
            return (Criteria) this;
        }

        public Criteria andTopPriorityLessThan(Integer value) {
            addCriterion("top_priority <", value, "topPriority");
            return (Criteria) this;
        }

        public Criteria andTopPriorityLessThanOrEqualTo(Integer value) {
            addCriterion("top_priority <=", value, "topPriority");
            return (Criteria) this;
        }

        public Criteria andTopPriorityIn(List<Integer> values) {
            addCriterion("top_priority in", values, "topPriority");
            return (Criteria) this;
        }

        public Criteria andTopPriorityNotIn(List<Integer> values) {
            addCriterion("top_priority not in", values, "topPriority");
            return (Criteria) this;
        }

        public Criteria andTopPriorityBetween(Integer value1, Integer value2) {
            addCriterion("top_priority between", value1, value2, "topPriority");
            return (Criteria) this;
        }

        public Criteria andTopPriorityNotBetween(Integer value1, Integer value2) {
            addCriterion("top_priority not between", value1, value2, "topPriority");
            return (Criteria) this;
        }

        public Criteria andVisitsIsNull() {
            addCriterion("visits is null");
            return (Criteria) this;
        }

        public Criteria andVisitsIsNotNull() {
            addCriterion("visits is not null");
            return (Criteria) this;
        }

        public Criteria andVisitsEqualTo(Long value) {
            addCriterion("visits =", value, "visits");
            return (Criteria) this;
        }

        public Criteria andVisitsNotEqualTo(Long value) {
            addCriterion("visits <>", value, "visits");
            return (Criteria) this;
        }

        public Criteria andVisitsGreaterThan(Long value) {
            addCriterion("visits >", value, "visits");
            return (Criteria) this;
        }

        public Criteria andVisitsGreaterThanOrEqualTo(Long value) {
            addCriterion("visits >=", value, "visits");
            return (Criteria) this;
        }

        public Criteria andVisitsLessThan(Long value) {
            addCriterion("visits <", value, "visits");
            return (Criteria) this;
        }

        public Criteria andVisitsLessThanOrEqualTo(Long value) {
            addCriterion("visits <=", value, "visits");
            return (Criteria) this;
        }

        public Criteria andVisitsIn(List<Long> values) {
            addCriterion("visits in", values, "visits");
            return (Criteria) this;
        }

        public Criteria andVisitsNotIn(List<Long> values) {
            addCriterion("visits not in", values, "visits");
            return (Criteria) this;
        }

        public Criteria andVisitsBetween(Long value1, Long value2) {
            addCriterion("visits between", value1, value2, "visits");
            return (Criteria) this;
        }

        public Criteria andVisitsNotBetween(Long value1, Long value2) {
            addCriterion("visits not between", value1, value2, "visits");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

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