import matplotlib.pyplot as plt
import numpy as np
from matplotlib.ticker import PercentFormatter

name = ('throughput', 'average time', 'sample time', 'singleshot time')
percentage = np.array([0.245, 0.344, 0.048, 0.022])
default = np.array([0.342, 0, 0, 0])
total = 13148

fig = plt.figure()

# absolute
ax1 = fig.add_subplot()
ax1.bar(name, default * total, bottom=percentage * total, color="orange", label="default benchmark mode")
ax1.bar(name, percentage * total, label="user-defined benchmark mode")
ax1.set_ylabel('# benchmarks')

# relative
ax2 = ax1.twinx()
plt.gca().yaxis.set_major_formatter(PercentFormatter(1, 0))
ax2.bar(name, default, bottom=percentage, color="orange", label="default benchmark mode")
ax2.bar(name, percentage, label="user-defined benchmark mode")
ax2.set_ylabel('# benchmarks [%]')

plt.legend()
ax1.set_xlabel(' ')
plt.tight_layout()
#plt.show()
plt.savefig('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\images\\mode_distribution.pdf')