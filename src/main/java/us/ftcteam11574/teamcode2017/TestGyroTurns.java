package us.ftcteam11574.teamcode2017;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous
public class TestGyroTurns extends Generic_Drive {
    @Override
    public void robotRun() {
        turn_to_heading(90, TURN_RIGHT, 0.25);
        stop_all_motors();
    }
}
