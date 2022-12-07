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

x_pointer, y_pointer = int(WINDOW_WIDTH/2), 50
center = (x_pointer,y_pointer)

magicTranslator = 1.0

angle1 = 0
speedX = 100
speedY = 100
speedFactor = 100

WIN.set_at(center, (0, 255, 0))

def move(x_pointer, y_pointer, angle1, rotationX, rotationY, radius1):
    for event in pygame.event.get():
        if event.type == QUIT:
            pygame.quit()
            sys.exit()

    speedX = int(speedFactor * radius1 * cos(angle1 * 3.141 / 180) * rotationX)
    x_pointer += speedX/speedFactor  # divided is python only

    speedY = int(speedFactor * radius1 * sin(angle1 * 3.141 / 180) * rotationY)
    y_pointer += speedY/speedFactor  # divided is python only

    angle1 += 1

    pygame.time.delay(10)

    WIN.set_at((int(x_pointer), int(y_pointer)), (255, 0, 0))

    pygame.draw.rect(WIN, (200, 200, 200), pygame.Rect(
        0, WINDOW_HEIGHT-50, 300, 50))
    WIN.blit(my_font.render("head at: " + str(int(x_pointer)) + ", "+str(int(y_pointer)) +
             " | " + str(int(angle1/3.6)) + "% ", False, (0, 0, 0)), (5, WINDOW_HEIGHT-45))
    WIN.blit(my_font.render("speed:" + str(speedX) + "|" +
             str(speedY), False, (0, 0, 0)), (5, WINDOW_HEIGHT-30))
    pygame.display.update()
    return (x_pointer, y_pointer)


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
            x = (x_pointer, y_pointer)
            for i in range(180):
                x = move(x[0], x[1], i, -1, 1, 1)
            for i in range(180):
                x = move(x[0], x[1], i, 1, 1, 1)
            print("breaker")
            for i in range(180):
                x = move(x[0], x[1], i, -1, 1, 1)
            
            for i in range(180):
                x = move(x[0], x[1], i, 1, 1, 1)

            for i in range(180):
                x = move(x[0], x[1], i, -1, 1, 1)

            for i in range(180):
                x = move(x[0], x[1], i, 1, -1, 5)


        runningHead = False

    # how it works: assume (0,0) and head up; drive to first location, head switch, drive to next ... repeat
