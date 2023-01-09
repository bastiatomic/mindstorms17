chainGear = (121 / 360) * 12/36
wheelGear = (135.7168 / 360) * 12/36

class Robot():
    realPosX = 0; realPosY = 0; thresholdX = 0; thresholdY = 0
    exactPosX = 0; exactPosY = 0
    roationCounterX = 0; roationCounterY = 0

myRobot = Robot()

def move (x, y):
    global thresholdX; global thresholdY
    global posX; global posY
    rotationsNeededA = (posX - x) / chainGear
    rotationsNeededB = (posY - y) / wheelGear

    thresholdX += (rotationsNeededA - int(rotationsNeededA))
    thresholdY += (rotationsNeededB - int(rotationsNeededB))

    rotationsNeededA += int(thresholdX)
    rotationsNeededB += int(thresholdY)

    thresholdX -= int(thresholdX)
    thresholdY -= int(thresholdY)

    speedA = 100
    timeForLength = rotationsNeededA / speedA
    speedB = rotationsNeededB / timeForLength 
        
    if(timeForLength == 0):
        speedB = 100  
    
    posX -= rotationsNeededA
    posY -= rotationsNeededB
    
    #print("rot: ",rotationsNeededA, rotationsNeededB)
    print("pos: ",posX, posY)

def moveToPos2 (x, y):

    # double rotationsNeeded
    newMoveX = myRobot.exactPosX - x
    newMoveY = myRobot.exactPosX - y

    degreeFromNewMoveX =newMoveX / chainGear
    degreeFromNewMoveY = newMoveY / wheelGear

    myRobot.exactPosX -= newMoveX #exactPos is a collection of moveX/moveY
    myRobot.exactPosY -= newMoveY


    
    #print("rot: ",rotationsNeededA, rotationsNeededB)
    print("newMove: ",newMoveX, newMoveY)
    print("pos: ",myRobot.exactPosX , myRobot.exactPosY )

posList = []

posList.append( (99, 8) )
#posList.append( (50, 20) )
posList.append( (99, 8) )
"""
posList.append( (101, 9) )
posList.append( (102, 9) )
posList.append( (102, 10) )
posList.append( (102, 11) )
posList.append( (102, 12) )
posList.append( (102, 13) )
posList.append( (102, 14) )
posList.append( (102, 15) )
posList.append( (102, 16) )
posList.append( (102, 17) )
posList.append( (101, 17) )
posList.append( (101, 18) )
posList.append( (101, 19) )
posList.append( (101, 20) )
posList.append( (100, 21) )
posList.append( (100, 22) )
posList.append( (99, 23) )
posList.append( (99, 24) )
posList.append( (98, 24) )
posList.append( (98, 25) )
posList.append( (98, 26) )
posList.append( (97, 26) )
posList.append( (97, 27) )
posList.append( (96, 27) )
posList.append( (96, 28) )
posList.append( (96, 29) )
posList.append( (95, 29) )
posList.append( (95, 30) )
posList.append( (94, 30) )
posList.append( (94, 31) )
posList.append( (93, 31) )
posList.append( (93, 32) )
posList.append( (92, 32) )
posList.append( (92, 33) )
posList.append( (91, 33) )
posList.append( (91, 34) )
posList.append( (90, 34) )
posList.append( (89, 34) )
posList.append( (89, 35) )
posList.append( (88, 35) )
posList.append( (88, 36) )
posList.append( (87, 36) )
posList.append( (86, 36) )
posList.append( (86, 37) )
posList.append( (85, 37) )
posList.append( (84, 37) )
posList.append( (83, 38) )
posList.append( (82, 38) )
posList.append( (81, 38) )
posList.append( (80, 39) )
posList.append( (79, 39) )
posList.append( (78, 39) )
posList.append( (77, 39) )
posList.append( (76, 39) )
posList.append( (76, 38) )
posList.append( (75, 38) )
posList.append( (75, 37) )
posList.append( (75, 36) )
posList.append( (75, 35) )
posList.append( (75, 34) )
posList.append( (75, 33) )
posList.append( (75, 32) )
posList.append( (75, 31) )
posList.append( (75, 30) )
posList.append( (76, 29) )
posList.append( (76, 28) )
posList.append( (76, 27) )
posList.append( (77, 27) )
posList.append( (77, 26) )
posList.append( (77, 25) )
posList.append( (78, 25) )
posList.append( (78, 24) )
posList.append( (78, 23) )
posList.append( (79, 23) )
posList.append( (79, 22) )
posList.append( (80, 21) )
posList.append( (80, 20) )
posList.append( (81, 20) )
posList.append( (81, 19) )
posList.append( (82, 19) )
posList.append( (82, 18) )
posList.append( (83, 18) )
posList.append( (83, 17) )
posList.append( (84, 17) )
posList.append( (84, 16) )
posList.append( (85, 16) )
posList.append( (85, 15) )
posList.append( (86, 15) )
posList.append( (86, 14) )
posList.append( (87, 14) )
posList.append( (87, 13) )
posList.append( (88, 13) )
posList.append( (89, 13) )
posList.append( (89, 12) )
posList.append( (90, 12) )
posList.append( (91, 11) )
posList.append( (92, 11) )
posList.append( (93, 10) )
posList.append( (94, 10) )
posList.append( (95, 10) )
posList.append( (95, 9) )
posList.append( (96, 9) )
posList.append( (97, 9) )
posList.append( (98, 9) )
posList.append( (97, 39) )
posList.append( (98, 39) )
posList.append( (99, 39) )
posList.append( (100, 39) )
posList.append( (101, 39) )
posList.append( (102, 39) )
posList.append( (103, 39) )
posList.append( (99, 8) )
"""

for obj in posList:
    moveToPos2(obj[0], obj[1])

