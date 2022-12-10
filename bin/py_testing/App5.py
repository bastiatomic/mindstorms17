from math import cos,sin
radius1 = 1

#TODO: No black pixel shall have direct neighboring pixels. then make a moveToCoord(x,y) repeatedly

angle1 = 180

speedMultiplier = 100

for angle1 in range(0, 360+1, 10):

    speedX = radius1 * cos(angle1 * 3.141 / 180) * speedMultiplier# this is how u manage the speed 

    speedY = radius1 * sin(angle1 * 3.141 / 180) * speedMultiplier

    print(angle1, ": ",("%.0f" % speedX),", ",("%.0f" % speedY))