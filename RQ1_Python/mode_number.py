import matplotlib.pyplot as plt
import numpy as np
from matplotlib.ticker import PercentFormatter

name = ('1', '2', '3', '4')
percentage = np.array([0.982, 0.013, 0.0015, 0.0037])
total = 13387

fig = plt.figure()

# absolute
ax1 = fig.add_subplot()
ax1.bar(name, percentage * total)
ax1.set_ylabel('# benchmarks')

# relative
ax2 = ax1.twinx()
plt.gca().yaxis.set_major_formatter(PercentFormatter(1, 0))
ax2.bar(name, percentage)
ax2.set_ylabel('# benchmarks [%]')

ax1.set_xlabel('# modes')
plt.tight_layout()
#plt.show()
plt.savefig('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\images\\mode_number.pdf')