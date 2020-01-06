import matplotlib.pyplot as plt
from matplotlib.ticker import PercentFormatter

name = ('throughput', 'average time', 'sample time', 'singleshot time', 'all')
percentage = (0.2557, 0.3519, 0.0572, 0.0264, 0.0037)
default = (0.3356, 0, 0, 0, 0)

plt.gca().yaxis.set_major_formatter(PercentFormatter(1))
plt.bar(name, default, bottom=percentage, color="orange", label="default benchmark mode")
plt.bar(name, percentage, label="user-defined benchmark mode")
plt.legend()
plt.ylabel('probability')
plt.xlabel(' ')
plt.tight_layout()
#plt.show()
plt.savefig('export.pdf')
