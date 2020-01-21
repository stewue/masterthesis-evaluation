import matplotlib.pyplot as plt
import numpy as np
import pandas as pd
from matplotlib.ticker import PercentFormatter

dataCov = pd.read_csv('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ2_Results\\time\\cov.csv', delimiter=";")
dataCi = pd.read_csv('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ2_Results\\time\\ci.csv', delimiter=";")
dataDivergence = pd.read_csv('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ2_Results\\time\\divergence.csv', delimiter=";")

cov = dataCov['time'] / 500
ci = dataCi['time'] / 500
divergence = dataDivergence['time'] / 500

valuesCov, baseCov = np.histogram(cov, bins=101, range=[0, 1], weights=np.ones(len(cov)) / len(cov))
cumulativeCov = np.cumsum(valuesCov)

valuesCi, baseCi = np.histogram(ci, bins=101, range=[0, 1], weights=np.ones(len(ci)) / len(ci))
cumulativeCi = np.cumsum(valuesCi)

valuesDivergence, baseDivergence = np.histogram(divergence, bins=101, range=[0, 1], weights=np.ones(len(divergence)) / len(divergence))
cumulativeDivergence = np.cumsum(valuesDivergence)

fig = plt.figure()
total = len(cov)

# absolute
ax1 = fig.add_subplot()
ax1.plot(baseCov[:-1], cumulativeCov * total, label="CoV", linestyle="-")
ax1.plot(baseCi[:-1], cumulativeCi * total, label="CI width", linestyle=":")
ax1.plot(baseDivergence[:-1], cumulativeDivergence * total, label="Divergence", linestyle="--")
ax1.set_ylabel('# benchmarks')
ax1.set_xlim(-0.01 * total, 1.01 * total)
ax1.set_ylim(-0.01 * total, 1.01 * total)

# relative
ax2 = ax1.twinx()
plt.gca().yaxis.set_major_formatter(PercentFormatter(1, 0))
ax2.plot(baseCov[:-1], cumulativeCov, label="CoV", linestyle="-")
ax2.set_ylabel('# benchmarks [%]')
ax2.set_xlim(-0.01, 1.01)
ax2.set_ylim(-0.01, 1.01)

ax2.scatter(0.61, 1, marker="^")
ax2.scatter(0.66, 1, marker="^")
ax2.scatter(0.63, 1, marker="^")

ax1.legend(loc='lower right')
ax1.set_xlabel('Time saved compared to the standard execution')
plt.xticks([0.0, 0.2, 0.4, 0.6, 0.8, 1.0], ["100%", "80%", "60%", "40%", "20%", "0%"])
plt.tight_layout()
#plt.show()
plt.savefig('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ2_Results\\images\\time_saved_cumulative.pdf')
