import matplotlib.pyplot as plt
import numpy as np
from matplotlib.ticker import PercentFormatter

label = [5, 10, 15, 20, 25]

combination = np.array([0.233, 0.179, 0.141, 0.116, 0.086])
ciRatio = np.array([0.172, 0.137, 0.120, 0.104, 0.094])
total = 3969

fig = plt.figure()
ylim = 1200

# absolute
ax1 = fig.add_subplot()
ax1.plot(label, combination * total, label="Wilcoxon + Cliff's Delta effect size", color="orange")
ax1.plot(label, ciRatio * total, label="CI ratio", linestyle="--", color="green")
ax1.set_ylabel('# benchmarks')
ax1.set_ylim(0, ylim)

# relative
ax2 = ax1.twinx()
plt.gca().yaxis.set_major_formatter(PercentFormatter(1, 0))
ax2.plot(label, combination, label="Wilcoxon + Cliff's Delta effect size", color="orange")
ax2.plot(label, ciRatio, label="CI ratio", linestyle="--", color="green")
ax2.set_ylabel('# benchmarks [%]')
ax2.set_ylim(0, ylim / total)

ax1.set_xlabel('# iterations')
plt.xticks(label)
plt.legend()
plt.tight_layout()
#plt.show()
plt.savefig('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ2_Results\\images\\measurement_size_single_fork.pdf')