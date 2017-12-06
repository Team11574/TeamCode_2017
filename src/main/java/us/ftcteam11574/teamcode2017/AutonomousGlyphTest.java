package us.ftcteam11574.teamcode2017;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="Autonomous Glyph Test", group="Autonomous")
@SuppressWarnings({"unused", "WeakerAccess"})
public class AutonomousGlyphTest extends Generic_Drive {
    @Override
    public void robotRun() {
        closeGrabber();
        sleep(CLAW_MOVEMENT_TIME);
        openGrabber();
        sleep(CLAW_MOVEMENT_TIME);
        closeGrabber();
        sleep(CLAW_MOVEMENT_TIME);
        positionGrabberLift(3);
        waitForGrabberLift();
        positionGrabberLift(0.5);
        waitForGrabberLift();


    }
}


