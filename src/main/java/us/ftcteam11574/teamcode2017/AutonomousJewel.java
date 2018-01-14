package us.ftcteam11574.teamcode2017;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="Autonomous Jewel and Park and Glyph", group="Autonomous")
@SuppressWarnings("unused")
public class AutonomousJewel extends Generic_Drive {
    @Override
    public void robotRun() {
        final AllianceColor ac = check_alliance();
        StartingPosition sp = getStartingPosition(ac);

        // Close grabber on glyph then raise it.
        raiseLeftJewel();
        raiseRightJewel();
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
            info("jewel is " + jewelColor + " alliance is " + ac);
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
            info("jewel is " + jewelColor + " alliance is " + ac);
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

        drive_distance(DRIVE_FORWARD, 19.0, 0.5);
        stop_all_motors();

        if (ac == AllianceColor.Blue) {
            drive_distance(STRAFE_RIGHT, 10.0, 0.5);
        } else {
            drive_distance(STRAFE_LEFT, 10.0, 0.5);
        }
        stop_all_motors();

        // Move to the Cryptobox.
        if (sp == StartingPosition.South) {
            // Drive to park in front of red Cryptobox.
            drive_distance(DRIVE_FORWARD, 18.0, 0.5);
            stop_all_motors();
            if (ac == AllianceColor.Blue)
                drive_distance(TURN_LEFT, 22, 0.25);
            else
                drive_distance(TURN_RIGHT, 22, 0.25);
            stop_all_motors();
            drive_distance(DRIVE_FORWARD, 150, 0.5);
            stop_all_motors();
        } else if (sp == StartingPosition.North) {
            // Drive to park in front of blue Cryptobox.
            drive_distance(DRIVE_FORWARD, 5.5, .5);
            stop_all_motors();
        }

        openGrabber();
        waitForClaw();
        positionGrabberLift(0.0);
        waitForGrabberLift();
        drive_distance(TURN_RIGHT, 3, 0.5);
        drive_distance(DRIVE_FORWARD, 5.0, 0.5);
        drive_distance(TURN_LEFT, 3, 0.5);
        drive_distance(DRIVE_BACKWARD, 3.5, 0.5);
        stop_all_motors();
    }
}