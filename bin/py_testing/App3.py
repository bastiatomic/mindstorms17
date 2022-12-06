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
WINDOW_WIDTH = 200
WINDOW_HEIGHT = 200 + 50
 
WIN = pygame.display.set_mode((WINDOW_WIDTH, WINDOW_HEIGHT))
pygame.display.set_caption('Ascende superius')

# The main function that controls the game
def main ():

    looping = True
    center = (110,120)
    x_pointer, y_pointer = 100,100
    radius = 20
    angle = 0

    # The main game loop
    while looping :
        fpsClock.tick(FPS)
        for event in pygame.event.get():
            if event.type == QUIT :
                pygame.quit()
                sys.exit()

        # how it works: assume (0,0) and head up; drive to first location, head switch, drive to next ... repeat
 
        pygame.time.delay(100)

        x_pointer = center[0] + (radius * cos(angle * 3.141 / 180))
        y_pointer = center[1] + (radius * sin(angle * 3.141 / 180))
        angle+=1
        
        WIN.set_at(center, (0,255,0))
        WIN.set_at(( int(x_pointer) , int(y_pointer)), (255,0,0))

        #pygame.draw.line(WIN,  (0,0,255), center, (x_pointer, y_pointer))
        
        pygame.draw.rect(WIN, (200,200,200), pygame.Rect(0, WINDOW_HEIGHT-50, 200, 50))
        WIN.blit(my_font.render("head at: " + str(int(x_pointer)) + ", "+str(int(y_pointer)) + "angle: " + str(angle), False, (0, 0, 0)), (5, WINDOW_HEIGHT-45))

        pygame.display.update()


        
main()
