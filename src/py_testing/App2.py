
import pygame, sys, random
from pygame.locals import *
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
 

def move(a,b):

    return a+b

# The main function that controls the game
def main ():

    looping = True
    x_pointer, y_pointer = 0,0
    head_position = False
    head_color_down = (255,0,0)
    head_color_up = (0,0,0)
    head_color = None
    change = 1
    pixelCounter = 0

    core_locations = [2265, 3, 260, 4, 3, 2, 126, 1, 7, 3, 249, 3, 10, 1, 126, 1, 12, 3, 240, 3, 14, 1, 126, 1, 16, 2, 234, 3, 17, 1, 126, 1, 19, 2, 229, 2, 20, 1, 126, 1, 21, 2, 224, 2, 23, 1, 127, 1, 23, 2, 220, 1, 24, 1, 128, 1, 24, 2, 217, 2, 24, 2, 129, 1, 25, 2, 214, 1, 26, 1, 131, 1, 26, 1, 211, 2, 26, 1, 132, 2, 26, 1, 209, 1, 27, 1, 135, 1, 26, 1, 207, 1, 26, 2, 137, 1, 26, 1, 205, 1, 26, 1, 140, 2, 25, 1, 203, 2, 24, 2, 143, 2, 24, 1, 202, 1, 24, 1, 146, 2, 23, 1, 201, 1, 23, 2, 149, 2, 22, 1, 200, 1, 20, 2, 155, 2, 19, 1, 200, 1, 18, 2, 159, 3, 16, 1, 200, 1, 15, 2, 165, 3, 13, 1, 200, 1, 11, 3, 173, 3, 8, 1, 201, 8, 149, 16, 51, 9, 127, 5, 9, 5, 42, 4, 16, 5, 103, 4, 24, 4, 34, 4, 19, 4, 112, 3, 25, 4, 28, 4, 30, 3, 93, 3, 36, 3, 24, 3, 31, 3, 103, 2, 36, 3, 18, 3, 41, 3, 85, 2, 46, 3, 12, 3, 41, 2, 96, 2, 46, 4, 4, 4, 51, 2, 79, 2, 56, 4, 51, 2, 90, 2, 114, 3, 73, 2, 117, 2, 85, 2, 119, 2, 69, 2, 121, 2, 81, 2, 123, 2, 65, 2, 125, 2, 77, 2, 127, 1, 64, 1, 128, 2, 73, 2, 129, 1, 64, 1, 130, 1, 71, 1, 130, 2, 65, 3, 128, 2, 68, 1, 128, 2, 71, 2, 128, 1, 65, 2, 127, 2, 75, 2, 127, 1, 63, 2, 126, 2, 79, 2, 126, 1, 61, 2, 125, 2, 83, 2, 125, 1, 60, 1, 125, 1, 86, 2, 125, 1, 58, 1, 124, 2, 89, 1, 124, 2, 56, 1, 124, 2, 91, 1, 124, 1, 55, 2, 123, 2, 93, 1, 124, 1, 54, 1, 124, 1, 95, 1, 123, 1, 53, 2, 123, 1, 96, 1, 124, 1, 52, 1, 123, 2, 97, 1, 123, 1, 52, 1, 123, 1, 98, 1, 123, 1, 52, 1, 123, 1, 98, 1, 124, 1, 50, 1, 124, 1, 99, 1, 123, 1, 50, 1, 123, 1, 100, 1, 123, 1, 50, 1, 123, 1, 100, 1, 123, 1, 50, 1, 123, 1, 99, 1, 124, 1, 50, 1, 124, 1, 98, 1, 124, 1, 50, 1, 124, 1, 98, 1, 124, 1, 50, 1, 124, 1, 98, 1, 124, 1, 50, 1, 125, 1, 96, 1, 124, 1, 52, 1, 124, 1, 95, 2, 124, 1, 52, 1, 125, 1, 94, 1, 125, 1, 52, 1, 126, 1, 92, 1, 126, 1, 53, 1, 125, 2, 90, 1, 126, 1, 54, 1, 126, 2, 87, 2, 127, 1, 54, 1, 128, 1, 85, 2, 127, 1, 56, 1, 128, 2, 81, 2, 129, 1, 56, 1, 130, 2, 77, 2, 130, 1, 58, 1, 131, 2, 73, 2, 132, 1, 58, 2, 132, 2, 69, 2, 133, 1, 60, 1, 135, 2, 63, 2, 135, 2, 61, 1, 137, 2, 56, 3, 138, 1, 62, 2, 139, 1, 53, 2, 139, 1, 64, 1, 140, 1, 52, 1, 139, 1, 66, 1, 138, 1, 54, 1, 137, 2, 67, 1, 136, 2, 55, 1, 136, 1, 69, 1, 135, 1, 57, 1, 134, 1, 71, 1, 133, 1, 58, 2, 132, 1, 73, 1, 131, 1, 60, 2, 130, 1, 75, 1, 129, 1, 62, 2, 128, 1, 77, 1, 127, 1, 64, 2, 125, 2, 79, 1, 125, 1, 67, 1, 123, 2, 81, 1, 122, 2, 69, 1, 121, 1, 84, 2, 119, 2, 71, 1, 119, 1, 86, 2, 117, 1, 74, 2, 115, 2, 89, 1, 114, 2, 77, 1, 113, 1, 92, 2, 111, 2, 79, 2, 109, 2, 95, 1, 108, 2, 83, 1, 107, 2, 97, 2, 105, 2, 85, 2, 103, 2, 101, 2, 101, 2, 89, 1, 101, 1, 105, 1, 99, 1, 92, 2, 97, 2, 107, 2, 95, 2, 95, 2, 93, 2, 111, 2, 43, 6, 42, 2, 99, 2, 36, 5, 6, 7, 35, 2, 116, 2, 30, 4, 17, 4, 30, 2, 105, 2, 26, 3, 25, 3, 26, 2, 121, 3, 21, 3, 31, 3, 21, 2, 111, 3, 16, 4, 35, 4, 17, 2, 128, 3, 11, 4, 41, 4, 10, 3, 121, 10, 49, 11]
    
    
    # The main game loop
    while looping :
        fpsClock.tick(FPS)
        for event in pygame.event.get():
            if event.type == QUIT :
                pygame.quit()
                sys.exit()

        keys=pygame.key.get_pressed()
        if keys[K_w]: y_pointer -= 1
        if keys[K_s]: y_pointer += 1
        if keys[K_a]: x_pointer -= 1
        if keys[K_d]: x_pointer += 1

        # how it works: assume (0,0) and head up; drive to first location, head switch, drive to next ... repeat
        for element in core_locations:

            if head_position:
                head_color = head_color_down
            else:
                head_color = head_color_up

            for i in range(element):

                pygame.time.delay(1)
                
                WIN.set_at((x_pointer, y_pointer), head_color)

                if head_color == head_color_down:
                    pixelCounter+=1
                
                pygame.draw.rect(WIN, (200,200,200), pygame.Rect(0, WINDOW_HEIGHT-50, 200, 50))
                WIN.blit(my_font.render("head at: " + str(x_pointer) + ", "+str(y_pointer), False, (0, 0, 0)), (5, WINDOW_HEIGHT-45))
                WIN.blit(my_font.render("printed: " +str(pixelCounter) + " | element: " + str(element), False, (0, 0, 0)), (5, WINDOW_HEIGHT-25))

                pygame.display.update()

                x_pointer += change
                if(x_pointer == WINDOW_WIDTH or x_pointer == -1):
                    y_pointer +=1
                    change = change*-1
                    x_pointer += change

                # debgging
                for event in pygame.event.get():
                    if event.type == QUIT :
                        pygame.quit()
                        sys.exit()


            head_position = not head_position
        #
        core_locations = []
        
main()