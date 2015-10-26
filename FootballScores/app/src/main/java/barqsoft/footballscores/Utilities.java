package barqsoft.footballscores;

import android.content.Context;
import java.util.HashMap;

/**
 * Created by yehya khaled on 3/3/2015.
 */
public class Utilities
{
    public static final int SERIE_A = 357;
    public static final int PREMIER_LEGAUE = 354;
    public static final int CHAMPIONS_LEAGUE = 362;
    public static final int PRIMERA_DIVISION = 358;
    public static final int BUNDESLIGA = 351;

    public static final Context mContext = ScoresApplication.getAppContext();
    private static HashMap<String, Integer> mTeamMap;

    private static String getString(int id)
    {
        return mContext.getResources().getString(id);
    }

    public static String getLeague(int league_num)
    {
        switch (league_num)
        {
            case SERIE_A : return getString(R.string.utility_serie_a);
            case PREMIER_LEGAUE : return getString(R.string.utility_premiere_league);
            case CHAMPIONS_LEAGUE : return getString(R.string.utility_uefa_league);
            case PRIMERA_DIVISION : return getString(R.string.utility_primera_division);
            case BUNDESLIGA : return getString(R.string.utility_bundesliga);
            default: return getString(R.string.utility_unknown_league);
        }
    }

    public static String getMatchDay(int match_day,int league_num)
    {
        if(league_num == CHAMPIONS_LEAGUE)
        {
            if (match_day <= 6)
            {
                return getString(R.string.utility_group_round);
            }
            else if(match_day == 7 || match_day == 8)
            {
                return getString(R.string.utility_knockout_round);
            }
            else if(match_day == 9 || match_day == 10)
            {
                return getString(R.string.utility_quarterfinal_round);
            }
            else if(match_day == 11 || match_day == 12)
            {
                return getString(R.string.utility_semifinal_round);
            }
            else
            {
                return getString(R.string.utility_final_round);
            }
        }
        else
        {
            return getString(R.string.utility_matchday) + String.valueOf(match_day);
        }
    }

    public static String getScores(int home_goals,int awaygoals)
    {
        if(home_goals < 0 || awaygoals < 0)
        {
            return " - ";
        }
        else
        {
            return String.valueOf(home_goals) + " - " + String.valueOf(awaygoals);
        }
    }

    public static int getTeamCrestByTeamName (String teamname)
    {
        if (mTeamMap == null)
        {
            mTeamMap = new HashMap<>();
            mTeamMap.put(getString(R.string.utility_arsenal),R.drawable.arsenal);
            mTeamMap.put(getString(R.string.utility_manchester),R.drawable.manchester_united);
            mTeamMap.put(getString(R.string.utility_swansea),R.drawable.swansea_city_afc);
            mTeamMap.put(getString(R.string.utility_leicester),R.drawable.leicester_city_fc_hd_logo);
            mTeamMap.put(getString(R.string.utility_everton),R.drawable.everton_fc_logo1);
            mTeamMap.put(getString(R.string.utility_westham),R.drawable.west_ham);
            mTeamMap.put(getString(R.string.utility_tottenham),R.drawable.tottenham_hotspur);
            mTeamMap.put(getString(R.string.utility_west_bromwich),R.drawable.west_bromwich_albion_hd_logo);
            mTeamMap.put(getString(R.string.utility_sunderland),R.drawable.sunderland);
            mTeamMap.put(getString(R.string.utility_stoke_city),R.drawable.stoke_city);
        }
        if (teamname == null) {
            return R.drawable.no_icon;
        }

        Integer result = mTeamMap.get(teamname);
        if(result == null) return R.drawable.no_icon;
        else return result;

    }
}
