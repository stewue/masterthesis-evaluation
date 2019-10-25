import matplotlib.pyplot as plt
import numpy as np

y = [

]
x = np.arange(2,len(y)+2,1)
plt.scatter(x, y)
plt.ylim(0,1)
plt.axhline(y=0.9)
#plt.show()
plt.savefig("export.png")