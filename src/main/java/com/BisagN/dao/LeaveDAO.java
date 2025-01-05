package com.BisagN.dao;

import java.util.ArrayList;

import org.springframework.stereotype.Service;
@Service
public interface LeaveDAO {
 public ArrayList<ArrayList<String>> LeaveReport();
 public ArrayList<ArrayList<String>> LeaveReportById(int viewid);
}


 