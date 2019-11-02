import numpy as np
import matplotlib.pyplot as plt
import pandas as pd
from matplotlib.ticker import PercentFormatter

data1000 = pd.read_csv('D:\\Masterarbeit\\Repo\\Evaluation\\RQ2_Results\\pre\\ci_relative_width_100_iterations_1_second.csv', delimiter=';')

filtered1000 = data1000[np.logical_not(np.isnan(data1000['i100']))]
all1000 = filtered1000['i100']

valuesAll1000, base1000 = np.histogram(all1000, bins=1000, range=[0,1], weights=np.ones(len(all1000)) / len(all1000))
cumulativeAll1000 = np.cumsum(valuesAll1000)
plt.plot(base1000[:-1], cumulativeAll1000, label="1'000 bootstrap simulation")

data10000 = pd.read_csv('D:\\Masterarbeit\\Repo\\Evaluation\\RQ2_Results\\pre\\10000_bootstrap_simulation\\ci_relative_width_100_iterations_1_second.csv', delimiter=';')

filtered10000 = data10000[np.logical_not(np.isnan(data10000['i100']))]
all10000 = filtered10000['i100']

valuesAll10000, base10000 = np.histogram(all10000, bins=1000, range=[0,1], weights=np.ones(len(all10000)) / len(all10000))
cumulativeAll10000 = np.cumsum(valuesAll10000)
plt.plot(base10000[:-1], cumulativeAll10000, label="10'000 bootstrap simulation")

plt.gca().yaxis.set_major_formatter(PercentFormatter(1))
plt.xlabel('CI width / mean')
plt.ylabel('cumulative probability')
plt.xlim(0, 0.3)
plt.legend()
plt.tight_layout()
plt.show()
#plt.savefig("export.png")