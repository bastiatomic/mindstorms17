
import time
import pygame, sys, random
from pygame.locals import *
pygame.init()
pygame.font.init()
 
# Colours
BACKGROUND = (255, 255, 255)
my_font = pygame.font.SysFont('Arial', 15)
 
# Game Setup
FPS = 10
fpsClock = pygame.time.Clock()
WINDOW_WIDTH = 500
WINDOW_HEIGHT = 500
 
WIN = pygame.display.set_mode((WINDOW_WIDTH, WINDOW_HEIGHT))
pygame.display.set_caption('Ascende superius')
 

def move(a,b):

    return a+b

def check_kill():
    for event in pygame.event.get():
        if event.type == QUIT :
            pygame.quit()
            sys.exit()

# The main function that controls the game
def main ():

    looping = True
    x_pointer, y_pointer = 5,1
    current_pos = [10,WINDOW_WIDTH/2]
    stop_painting = True

    # The main game loop
    while looping:
        fpsClock.tick(FPS)
        for event in pygame.event.get():
            if event.type == QUIT :
                pygame.quit()
                sys.exit()
            if event.type == pygame.KEYDOWN:
                if event.key == pygame.K_w: y_pointer-=1
                if event.key == pygame.K_s: y_pointer+=1
                if event.key == pygame.K_a: x_pointer-=1
                if event.key == pygame.K_d: x_pointer+=1

        list = [10,9,8,7,6,5,4,3,2,1,0,-1,-2,-3,-4,-5,-6,-7,-8,-9,-10]
        if(stop_painting):
            for _ in range(10):
                for i in list:
                    current_pos[0]+=1; current_pos[1]+= i/2
                    pygame.draw.rect(WIN, (200,200,200), pygame.Rect(current_pos[0], current_pos[1], 2, 2)) 
                    pygame.draw.rect(WIN, (200,200,200), pygame.Rect(0, WINDOW_HEIGHT-50, 200, 50))        
                    WIN.blit(my_font.render("head at: " + str(current_pos[0]) + ", "+str(current_pos[1]), False, (255, 0, 0)), (5, WINDOW_HEIGHT-45))
                    pygame.display.flip()
                    time.sleep(0.1)
                    check_kill()
                
                list.reverse()
                

                
            stop_painting = False
        
 

main()