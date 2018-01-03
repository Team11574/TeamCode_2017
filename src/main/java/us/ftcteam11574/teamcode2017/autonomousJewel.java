/*
package us.ftcteam11574.teamcode2017;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;


@Autonomous(name = "Autonomous Jewel and Park", group="Autonomous")
@SuppressWarnings("unused")
public class autonomousJewel extends Generic_Drive {

    @Override
        public void robotRun() {
            final AllianceColor ac = check_alliance();
            StartingPosition sp = getStartingPosition(ac);

            if (sp == StartingPosition.South) {
                int strafe_direction;
                if (ac == AllianceColor.Blue)
                    strafe_direction = STRAFE_LEFT;
                else
                    strafe_direction = STRAFE_RIGHT;

                // Drive to park in front of Cryptobox.
                drive_distance(DRIVE_FORWARD, 6.0, 0.25);
                drive_distance(DRIVE_FORWARD, 24.0, 0.5);
                stop_all_motors();
                drive_distance(strafe_direction, 11.0, 0.5);
                stop_all_motors();
            }  if (sp == StartingPosition.North) {
        }   else if (ac == AllianceColor.Blue)
            int strafe_direction;
                strafe_direction = STRAFE_RIGHT;
                strafe_direction = STRAFE_LEFT;

                // Drive to park in front of Cryptobox.
                drive_distance(DRIVE_FORWARD, 18.0, 0.5);
                stop_all_motors();
                drive_distance(strafe_direction, 10.0, 0.5);
                stop_all_motors();
                drive_distance(DRIVE_FORWARD, 5.5, .5);
                stop_all_motors();
                drive_distance(DRIVE_FORWARD, 2.5, 0.5);
                 stop_all_motors();


            }
        }
    }
*/