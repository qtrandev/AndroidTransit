package org.codeformiami.transittracker;

import org.codeformiami.transittracker.model.BusResult;
import org.codeformiami.transittracker.model.TrackerResult;

import retrofit.Callback;
import retrofit.http.GET;

public interface MiamiTransitApiService {
    @GET("/api/Buses.json")
    void buses(Callback<BusResult> cb);

    @GET("/tracker.json")
    void trackers(Callback<TrackerResult> cb);
}