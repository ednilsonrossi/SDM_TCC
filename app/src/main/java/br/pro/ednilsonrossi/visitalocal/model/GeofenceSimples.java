package br.pro.ednilsonrossi.visitalocal.model;

import com.google.android.gms.location.Geofence;

public class GeofenceSimples {

    private static final long MILLISECONDS_PER_SECOND = 1000;
    private static final long SECONDS_PER_MINUTE = 60;
    private static final long MINUTES_PER_HOUR = 60;
    private static final long GEOFENCE_DURATION_IN_HOURS = 12;
    public static final long DEFAULT_GEOFENCE_EXPITARION_TIME =
            GEOFENCE_DURATION_IN_HOURS *
                    MINUTES_PER_HOUR *
                    SECONDS_PER_MINUTE *
                    MILLISECONDS_PER_SECOND;
    public static final float DEFAULT_RADIUS_IN_METERS = 100.0f;


    private final String mId;
    private final double mLatitude;
    private final double mLongitude;
    private final float mRadius;
    private final long mExpirationDuration;
    private final int mTransitionType;


    public GeofenceSimples(String geofenceIdentificador, double latitude, double longitude, float raio, long tempoDuracao, int tipoTransacao) {
        this.mId = geofenceIdentificador;
        this.mLatitude = latitude;
        this.mLongitude = longitude;
        this.mRadius = raio;
        this.mExpirationDuration = tempoDuracao;
        this.mTransitionType = tipoTransacao;
    }

    public Geofence toGeofence(){
        Geofence.Builder builder = new Geofence.Builder();
        builder.setRequestId(mId);
        builder.setTransitionTypes(mTransitionType);
        builder.setCircularRegion(mLatitude, mLongitude, mRadius);
        builder.setExpirationDuration(mExpirationDuration);
        return builder.build();
    }

    public String getIdentificador() {
        return mId;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public float getRadius() {
        return mRadius;
    }

    public long getExpirationDuration() {
        return mExpirationDuration;
    }

    public int getTransitionType() {
        return mTransitionType;
    }
}
