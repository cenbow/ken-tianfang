package com.tianfang.business.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * 赛事
 * 
 * @author wangxiang
 *
 *         2015年11月17日
 */
public class SportRaceDto implements Serializable {

	private static final long serialVersionUID = -237837061467801187L;

	@Getter
	@Setter
	private String id;

	@Getter
	@Setter
	private String name; // 赛事名称

	@Getter
	@Setter
	private Integer type; // 赛事类型 (1.友谊赛)

	@Getter
	@Setter
	private Date raceTime; // 对战时间

	@Getter
	@Setter
	private String raceTimeStr;
	
	@Getter
	@Setter
	private String raceTimeDay;	// 对战日期
	
	@Getter
	@Setter
	private String raceTimeHour; // 对战时间

	@Getter
	@Setter
	private String raceTimeS; // 对战开始时间(查询条件)

	@Getter
	@Setter
	private String raceTimeE; // 对战结束时间(查询条件)

	@Getter
	@Setter
	private String raceAddress; // 比赛地点

	@Getter
	@Setter
	private String homeTeam; // 主场球队

	@Getter
	@Setter
	private String homeTeamName; // 通过联表查询获得主场球队名

	@Getter
	@Setter
	private String homeTeamLogo; // 通过联表查询获得主场球队logo

	@Getter
	@Setter
	private String vsTeam; // 客场球队

	@Getter
	@Setter
	private String vsTeamName; // 通过联表查询获得客场球队名

	@Getter
	@Setter
	private String vsTeamLogo; // 通过联表查询获得客场球队logo

	@Getter
	@Setter
	private Integer homeTeamNumber; // 主场球队得分

	@Getter
	@Setter
	private Integer vsTeamNumber; // 客场球队得分

	@Getter
	@Setter
	private Integer raceStatus; // 赛事类型(0.比赛未开始,1.进行中,2.已结束)

	@Getter
	@Setter
	private Integer raceNumber; // 场次(保留暂不用)

	@Getter
	@Setter
	private Date createTime; // 创建时间

	@Getter
	@Setter
	private String createTimeStr;

	@Getter
	@Setter
	private String startTimeStr;

	@Getter
	@Setter
	private String endTimeStr;

	@Getter
	@Setter
	private Date lastUpdateTime; // 最近一次的更新时间

	@Getter
	@Setter
	private Integer stat; // 状态是否有效：1表示有效；0表示无效
	
	@Getter
	@Setter
	private String teamId;// 查询含有该球队的比赛
	
	@Getter
	@Setter
	private String index;// 该球队的第几场比赛 
}