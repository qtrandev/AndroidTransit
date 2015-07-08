package org.codeformiami.transittracker;

import org.codeformiami.transittracker.model.BusResult;
import org.codeformiami.transittracker.model.TrackerResult;
import org.codeformiami.transittracker.model.TrainResult;
import org.codeformiami.transittracker.model.TrainRouteResult;

import retrofit.Callback;
import retrofit.http.GET;

public interface MiamiTransitApiService {
    @GET("/api/Buses.json")
    void buses(Callback<BusResult> cb);

    @GET("/tracker.json")
    void trackers(Callback<TrackerResult> cb);

    @GET("/api/Trains.json")
    void trains(Callback<TrainResult> cb);

    @GET("/api/TrainMapShape.json")
    void trainRoutes(Callback<TrainRouteResult> cb);
}