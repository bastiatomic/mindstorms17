import pygame, sys, random
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
WINDOW_WIDTH = 500
WINDOW_HEIGHT = 500 + 50
 
WIN = pygame.display.set_mode((WINDOW_WIDTH, WINDOW_HEIGHT))
pygame.display.set_caption('Ascende superius')

# The main function that controls the game
def main ():

    i = 0
    isIncrementAllowed = True
    looping = True
    radius = 1
    center = ( int(WINDOW_WIDTH/2), int(WINDOW_WIDTH/2+radius))
    x_pointer, y_pointer =int(WINDOW_WIDTH/2), int(WINDOW_WIDTH/2)
    
    angle = 0
    speedX = 100
    speedY = 100
    speedFactor  =100
    WIN.set_at(center, (0,255,0))

    # The main game loop
    while looping :
        fpsClock.tick(FPS)
        for event in pygame.event.get():
            if event.type == pygame.KEYDOWN:
                if event.key == pygame.K_SPACE:
                    isIncrementAllowed = not isIncrementAllowed

            if event.type == QUIT :
                pygame.quit()
                sys.exit()

        # how it works: assume (0,0) and head up; drive to first location, head switch, drive to next ... repeat
        if(angle < 360):

            speedX = int(speedFactor*radius * cos(angle * 3.141 / 180))
            x_pointer += speedX/speedFactor #divided is python only

            speedY = int(speedFactor*radius * sin(angle * 3.141 / 180))
            y_pointer += speedY/speedFactor #divided is python only

            angle+=1

            pygame.time.delay(50)

            WIN.set_at(( int(x_pointer) , int(y_pointer)), (255,0,0))

        pygame.draw.rect(WIN, (200,200,200), pygame.Rect(0, WINDOW_HEIGHT-50, 300, 50))
        WIN.blit(my_font.render("head at: " + str(int(x_pointer)) + ", "+str(int(y_pointer)) +" | "+ str(int(angle/3.6)) +"% ", False, (0, 0, 0)), (5, WINDOW_HEIGHT-45))
        WIN.blit(my_font.render("speed:" + str(speedX) + "|"+ str(speedY), False, (0, 0, 0)), (5, WINDOW_HEIGHT-30))
        pygame.display.update()


        
main()
