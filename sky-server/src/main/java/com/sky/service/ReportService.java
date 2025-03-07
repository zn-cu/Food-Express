package com.sky.service;

import com.sky.vo.OrderReportVO;
import com.sky.vo.SalesTop10ReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;

/**
 * @author 夙落陌上梦
 * @version 1.0
 * @ClassName ReportService
 * @DateTime 2025/3/7 下午1:33
 * @Description:
 */
public interface ReportService {

    //根据时间区间统计营业额
    TurnoverReportVO getTurnover(LocalDate begin, LocalDate end);
    //根据时间区间统计用户数量
    UserReportVO getUserStatistics(LocalDate begin, LocalDate end);

    //根据时间统计订单数量
    OrderReportVO getOrderStatistics(LocalDate begin, LocalDate end);
    //销量统计排名
    SalesTop10ReportVO getSalesTop10(LocalDate begin, LocalDate end);
    //导出近30天的运营数据报表
    void exportBusinessData(HttpServletResponse response);
}
