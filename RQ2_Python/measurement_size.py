import matplotlib.pyplot as plt
from matplotlib.ticker import PercentFormatter

label = [5,10,15,20,25]
combination = [0.22, 0.17, 0.13, 0.11, 0.08]
ciRatio = [0.15, 0.12, 0.10, 0.10, 0.09]

plt.gca().yaxis.set_major_formatter(PercentFormatter(1))
plt.plot(label, combination, label="Wilcoxon + effect size")
plt.plot(label, ciRatio, label="CI ratio")
plt.ylabel('# benchmarks where a significant change is detected')
plt.xlabel('# iterations')
plt.ylim(0, 0.3)
plt.xticks(label)
plt.legend()
plt.tight_layout()
#plt.show()
plt.savefig('export.png')

