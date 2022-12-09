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

looping = True

x_pointer, y_pointer = int(WINDOW_WIDTH/2), int(WINDOW_HEIGHT/2)

angle1 = 0
radius1 = 1
speedX = 0
speedY = 0
angleFactor = -1

WIN.set_at((x_pointer, y_pointer), (0, 255, 0))

# The main game loop
runningHead = True
while looping:
    fpsClock.tick(FPS)
    for event in pygame.event.get():

        if event.type == QUIT:
            pygame.quit()
            sys.exit()

    if(runningHead):
  
        speedX = radius1 * cos(angle1 * 3.141 / 180) # this is how u manage the speed 
        x_pointer += speedX

        speedY = radius1 * sin(angle1 * 3.141 / 180)
        y_pointer += speedY

        angle1 += 1

        if angle1 > 360:
            runningHead = not runningHead

        pygame.time.delay(10)
        
        WIN.set_at((int(x_pointer), int(y_pointer)), (255, 0, 0))

        pygame.draw.rect(WIN, (200, 200, 200), pygame.Rect(0, WINDOW_HEIGHT-70, 300, 70))
        WIN.blit(my_font.render("head at: " + str((x_pointer)) + ", "+str((y_pointer)), False, (0, 0, 0)), (5, WINDOW_HEIGHT-60))
        WIN.blit(my_font.render("speed:" + str("%.2f" % speedX) + "|" + str("%.2f" % speedY), False, (0, 0, 0)), (5, WINDOW_HEIGHT-45))
        WIN.blit(my_font.render("angle:" + str(angle1), False, (0, 0, 0)), (5, WINDOW_HEIGHT-15))
        pygame.display.update()
