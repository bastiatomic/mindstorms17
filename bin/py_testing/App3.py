import pygame
import sys
import random
from pygame.locals import *
from math import cos, sin
pygame.init()
pygame.font.init()

# Colours
BACKGROUND = (255, 255, 255)
my_font = pygame.font.SysFont('Arial', 15)

# Game Setup
FPS = 30
fpsClock = pygame.time.Clock()
WINDOW_WIDTH = 700
WINDOW_HEIGHT = 700 + 50

WIN = pygame.display.set_mode((WINDOW_WIDTH, WINDOW_HEIGHT))
pygame.display.set_caption('Ascende superius')

# The main function that controls the game

i = 0
isIncrementAllowed = True
looping = True

x_pointer, y_pointer = int(WINDOW_WIDTH/2), int(WINDOW_WIDTH/2)
fakeX, fakeY = 0,0
center = (x_pointer,y_pointer)

magicTranslator = 1.0

angle1 = 0
speedX = 100
speedY = 100
speedFactor = 1

WIN.set_at(center, (0, 255, 0))

def move(x_pointer, y_pointer, fakeX, fakeY, angle1, rotationX, rotationY, radius1):
    for event in pygame.event.get():
        if event.type == QUIT:
            pygame.quit()
            sys.exit()

    speedX = radius1 * cos(angle1 * 3.141 / 180)

    fakeX += speedX
    removalX = int(fakeX) # lejos and stuff
    fakeX -= removalX
    x_pointer += (removalX * rotationX)

    speedY = radius1 * sin(angle1 * 3.141 / 180)

    fakeY += speedY
    removalY = int(fakeY) # lejos and stuff requires this
    fakeY -= removalY
    y_pointer += (removalY * rotationY)

    angle1 += 1

    pygame.time.delay(10)

    WIN.set_at((int(x_pointer), int(y_pointer)), (255, 0, 0))

    pygame.draw.rect(WIN, (200, 200, 200), pygame.Rect(0, WINDOW_HEIGHT-70, 300, 70))
    WIN.blit(my_font.render("head at: " + str((x_pointer)) + ", "+str((y_pointer)), False, (0, 0, 0)), (5, WINDOW_HEIGHT-60))
    WIN.blit(my_font.render("speed:" + str(speedX) + "|" + str(speedY), False, (0, 0, 0)), (5, WINDOW_HEIGHT-45))
    WIN.blit(my_font.render("rem:" + str(removalX) + "|" + str(removalY), False, (0, 0, 0)), (5, WINDOW_HEIGHT-30))
    WIN.blit(my_font.render("fake:" + str(fakeX) + "|" + str(fakeY), False, (0, 0, 0)), (5, WINDOW_HEIGHT-15))
    pygame.display.update()
    return (x_pointer, y_pointer, fakeX, fakeY)


# The main game loop
runningHead = True
while looping:
    fpsClock.tick(FPS)
    for event in pygame.event.get():
        if event.type == pygame.KEYDOWN:
            if event.key == pygame.K_SPACE:
                isIncrementAllowed = not isIncrementAllowed

        if event.type == QUIT:
            pygame.quit()
            sys.exit()

        if(runningHead):
            x = (x_pointer, y_pointer, fakeX, fakeY)

            for i in range(180):
                x = move(x[0], x[1], x[2], x[3], i, 1.5, 1, 1)

            for i in range(180,360):
                x = move(x[0], x[1], x[2], x[3], i, 1.5, 1, 1)

        runningHead = False

    # how it works: assume (0,0) and head up; drive to first location, head switch, drive to next ... repeat
