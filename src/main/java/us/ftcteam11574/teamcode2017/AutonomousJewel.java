package us.ftcteam11574.teamcode2017;

import android.support.annotation.NonNull;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="Autonomous Jewel and Park and Glyph", group="Autonomous")
@SuppressWarnings("unused")
public class AutonomousJewel extends Generic_Drive {
    @Override
    public void robotRun() {
        final AllianceColor ac = check_alliance();
        StartingPosition sp = getStartingPosition(ac);

        // Close grabber on glyph then raise it.
        closeGrabber();
        waitForClaw();
        positionGrabberLift(3.0);
        waitForGrabberLift();

        int bumpDirection;
        int returnDirection;

        if (ac == AllianceColor.Blue) {
            lowerLeftJewel();
            waitForJewelArm();
            AllianceColor jewelColor = checkJewelColor(JewelColorLeft);
            if (jewelColor == ac) {
                bumpDirection = TURN_LEFT;
                returnDirection = TURN_RIGHT;
            } else {
                bumpDirection = TURN_RIGHT;
                returnDirection = TURN_LEFT;
            }
        } else {
            lowerRightJewel();
            waitForJewelArm();
            AllianceColor jewelColor = checkJewelColor(JewelColorRight);
            if (jewelColor != ac) {
                bumpDirection = TURN_LEFT;
                returnDirection = TURN_RIGHT;
            } else {
                bumpDirection = TURN_RIGHT;
                returnDirection = TURN_LEFT;
            }
        }

        drive_distance(bumpDirection, 3, 0.2);
        stop_all_motors();

        if (ac == AllianceColor.Blue) {
            raiseLeftJewel();
        } else {
            raiseRightJewel();
        }
        waitForJewelArm();

        drive_distance(returnDirection, 3, 0.2);
        stop_all_motors();

        // move to cryptobox using ifs
        if (sp == StartingPosition.South) {
            int strafe_direction;
            if (ac == AllianceColor.Blue) {
                strafe_direction = STRAFE_LEFT;
            } else {
                strafe_direction = STRAFE_RIGHT;
            }

            // Drive to park in front of red Cryptobox.
            drive_distance(DRIVE_FORWARD, 6.0, 0.25);
            drive_distance(DRIVE_FORWARD, 24.0, 0.5);
            stop_all_motors();
            drive_distance(strafe_direction, 11.0, 0.5);
            stop_all_motors();
        } else if (sp == StartingPosition.North) {
            int strafe_direction;
            if (ac == AllianceColor.Blue)
                strafe_direction = STRAFE_RIGHT;
            else
                strafe_direction = STRAFE_LEFT;
            // Drive to park in front of blue Cryptobox.
            drive_distance(DRIVE_FORWARD, 18.0, 0.5);
            stop_all_motors();
            drive_distance(strafe_direction, 10.0, 0.5);
            stop_all_motors();
            drive_distance(DRIVE_FORWARD, 5.5, .5);
            stop_all_motors();


        }
        openGrabber();
        waitForClaw();



    }


}
