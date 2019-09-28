import matplotlib.pyplot as plt
from matplotlib.ticker import PercentFormatter

name = ('throughput', 'averageTime', 'sampleTime', 'singleShotTime', 'all')
percentage = (0.2557, 0.3519, 0.0572, 0.0264, 0.0037)
default = (0.3356, 0, 0, 0, 0)

plt.gca().yaxis.set_major_formatter(PercentFormatter(1))
plt.bar(name, percentage, label="manual set")
plt.bar(name, default, bottom=percentage, label="default")
plt.legend()
plt.tight_layout()
#plt.show()
plt.savefig('export.png')
