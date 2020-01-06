import matplotlib.pyplot as plt
import numpy as np
import pandas as pd
from matplotlib.ticker import PercentFormatter

dataCov = pd.read_csv('D:\\cov.csv', delimiter=";")
dataCi = pd.read_csv('D:\\ci.csv', delimiter=";")
dataDivergence = pd.read_csv('D:\\divergence.csv', delimiter=";")

cov = dataCov['time'] / 500
ci = dataCi['time'] / 500
divergence = dataDivergence['time'] / 500

valuesCov, baseCov = np.histogram(cov, bins=101, range=[0, 1], weights=np.ones(len(cov)) / len(cov))
cumulativeCov = np.cumsum(valuesCov)
plt.plot(baseCov[:-1], cumulativeCov, label="CoV", linestyle="-")

valuesCi, baseCi = np.histogram(ci, bins=101, range=[0, 1], weights=np.ones(len(ci)) / len(ci))
cumulativeCi = np.cumsum(valuesCi)
plt.plot(baseCi[:-1], cumulativeCi, label="CI width", linestyle=":")

valuesDivergence, baseDivergence = np.histogram(divergence, bins=101, range=[0, 1], weights=np.ones(len(divergence)) / len(divergence))
cumulativeDivergence = np.cumsum(valuesDivergence)
plt.plot(baseDivergence[:-1], cumulativeDivergence, label="Divergence", linestyle="--")

plt.scatter(0.61, 1, marker="^")
plt.scatter(0.66, 1, marker="^")
plt.scatter(0.63, 1, marker="^")

plt.gca().yaxis.set_major_formatter(PercentFormatter(1))
plt.legend(loc='lower right')
plt.xlabel('Time saved compared to the standard execution')
plt.xlim(-0.01, 1.01)
plt.ylim(-0.01, 1.01)
plt.ylabel('cumulative probability')
plt.xticks([0.0, 0.2, 0.4, 0.6, 0.8, 1.0], ["100%", "80%", "60%", "40%", "20%", "0%"])
plt.tight_layout()
#plt.show()
plt.savefig('export.pdf')
