package org.codeformiami.transittracker.model;

import java.util.List;

public class TrainRouteResult {

    public RecordSet RecordSet;

    public class RecordSet {
        public List<TrainRoutePosition> Record;
    }

    public class TrainRoutePosition {
        public String LineID;
        public String OrderNum;
        public double Latitude;
        public double Longitude;
    }
}