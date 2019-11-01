import matplotlib.pyplot as plt
import numpy as np
import pandas as pd

data = pd.read_csv('D:\\Masterarbeit\\Repo\\Evaluation\\RQ2_Results\\pre\\pdf_last_difference.csv', delimiter=';')

plt.hist(data['i10_i9'], bins=2000, alpha=0.4, label="variability of 10 second \n iterations between the 9th \n and 10th iteration")
plt.hist(data['i100_i90'], bins=2000, alpha=0.4, label="variability of 1 second \n iterations between the 90th \n and 100th iteration")
plt.xlim(-0.05, 0.05)
plt.legend()
plt.xlabel("p value shift")
plt.ylabel("# benchmarks")
plt.tight_layout()
#plt.show()
plt.savefig("export.png")

print("10 iterations")
print("mean: " + str(np.average(data['i10_i9'])))
print("std: " + str(np.std(data['i10_i9'])))

print("")

print("100 iterations")
print("mean: " + str(np.average(data['i100_i90'])))
print("std: " + str(np.std(data['i100_i90'])))