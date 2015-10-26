package barqsoft.footballscores.widget;

import android.annotation.TargetApi;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.text.SimpleDateFormat;
import java.util.Date;

import barqsoft.footballscores.DatabaseContract;
import barqsoft.footballscores.R;


/**
 * RemoteViewsService controlling the data being shown in the scrollable scores widget
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class ScoreWidgetRemoteViewsService extends RemoteViewsService {
    public final String LOG_TAG = ScoreWidgetRemoteViewsService.class.getSimpleName();
    private static final String[] SCORE_COLUMNS = {
            DatabaseContract.SCORES_TABLE + "." + DatabaseContract.scores_table._ID,
            DatabaseContract.scores_table.AWAY_COL,
            DatabaseContract.scores_table.HOME_COL,
            DatabaseContract.scores_table.TIME_COL,
            DatabaseContract.scores_table.DATE_COL,
            DatabaseContract.scores_table.AWAY_GOALS_COL,
            DatabaseContract.scores_table.HOME_GOALS_COL
    };
    // these indices must match the projection
    static final int INDEX_SCORES_ID = 0;
    static final int INDEX_SCORES_AWAY = 1;
    static final int INDEX_SCORES_HOME = 2;
    static final int INDEX_SCORES_TIME = 3;
    static final int INDEX_SCORES_DATE = 4;
    static final int INDEX_SCORES_AWAY_GOALS = 5;
    static final int INDEX_SCORES_HOME_GOALS = 6;

    private static final SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RemoteViewsFactory() {
            private Cursor data = null;

            @Override
            public void onCreate() {
                // Nothing to do
            }

            @Override
            public void onDataSetChanged() {
                if (data != null) {
                    data.close();
                }
                // This method is called by the app hosting the widget (e.g., the launcher)
                // However, our ContentProvider is not exported so it doesn't have access to the
                // data. Therefore we need to clear (and finally restore) the calling identity so
                // that calls use our process and permission
                final String[] todaysDate = new String []{
                    mFormat.format(new Date(System.currentTimeMillis()))
                };
                final long identityToken = Binder.clearCallingIdentity();
                Uri queryUri = DatabaseContract.scores_table.buildScoreWithDate();
                data = getContentResolver().query(queryUri,
                        SCORE_COLUMNS,
                        null,
                        todaysDate,
                        DatabaseContract.scores_table.TIME_COL + " ASC");
                Binder.restoreCallingIdentity(identityToken);
            }

            @Override
            public void onDestroy() {
                if (data != null) {
                    data.close();
                    data = null;
                }
            }

            @Override
            public int getCount() {
                return data == null ? 0 : data.getCount();
            }

            @Override
            public RemoteViews getViewAt(int position) {
                if (position == AdapterView.INVALID_POSITION ||
                        data == null || !data.moveToPosition(position)) {
                    return null;
                }
                RemoteViews views = new RemoteViews(getPackageName(), R.layout.widget_scores_list_item);

//                String description = data.getString(INDEX_WEATHER_DESC);
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
//                    setRemoteContentDescription(views, description);
//                }

                views.setTextViewText(R.id.widget_away_text, data.getString(INDEX_SCORES_AWAY));
                views.setTextViewText(R.id.widget_home_text, data.getString(INDEX_SCORES_HOME));

                final int awayGoals = data.getInt(INDEX_SCORES_AWAY_GOALS);
                final int homeGoals = data.getInt(INDEX_SCORES_HOME_GOALS);

                // -1 indicates the game has not occurred, just show time
                if (awayGoals == -1 || homeGoals == -1) {
                    views.setTextViewText(R.id.widget_score_text, getResources().getString(R.string.empty_score));

                }
                else {
                    views.setTextViewText(R.id.widget_score_text,
                            Integer.toString(awayGoals) + " - " + Integer.toString(homeGoals));
                }

                views.setTextViewText(R.id.widget_game_time_text, data.getString(INDEX_SCORES_TIME));
//
//                final Intent fillInIntent = new Intent();
//                String locationSetting =
//                        Utility.getPreferredLocation(com.example.android.sunshine.app.widget.ScoreWidgetRemoteViewsService.this);
//                Uri weatherUri = WeatherContract.WeatherEntry.buildWeatherLocationWithDate(
//                        locationSetting,
//                        dateInMillis);
//                fillInIntent.setData(weatherUri);
//                views.setOnClickFillInIntent(R.id.widget_list_item, fillInIntent);
                return views;
            }

            @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
            private void setRemoteContentDescription(RemoteViews views, String description) {
//                views.setContentDescription(R.id.widget_icon, description);
            }

            @Override
            public RemoteViews getLoadingView() {
                return new RemoteViews(getPackageName(), R.layout.widget_scores_list_item);
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public long getItemId(int position) {
                if (data.moveToPosition(position))
                    return data.getLong(INDEX_SCORES_ID);
                return position;
            }

            @Override
            public boolean hasStableIds() {
                return true;
            }
        };
    }
}
